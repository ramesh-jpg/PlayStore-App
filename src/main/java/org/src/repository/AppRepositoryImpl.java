package org.src.repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.src.model.App;
import org.src.model.Review;
import org.src.model.User;

/**
 * Implementation of the {@link AppRepository} interface for database operations.
 *
 * <p>This class handles CRUD operations for Apps, including managing related entities like Authors,
 * Features, and Reviews. It uses JDBC for database connectivity.
 */
@Repository
public class AppRepositoryImpl implements AppRepository {

  private final DataSource dataSource;

  @Autowired
  public AppRepositoryImpl(final DataSource dataSource) {
    this.dataSource = dataSource;
  }

  /**
   * Saves a new App to the database along with its features and author.
   *
   * <p>If the author does not exist, a new author is created.
   *
   * @param app the App object containing details to be saved
   */
  @Override
  public void save(final App app) {
    final String insertApp = "INSERT INTO app ( name, description, version, rating, "
            + "installed_count, author_id) VALUES ( ?, ?, ?, ?, ?, ?)RETURNING id";
    final String insertFeatures = "INSERT INTO features (app_id, features) VALUES (?, ?)";

    try (final Connection connection = dataSource.getConnection()) {
      try (final PreparedStatement statement = connection.prepareStatement(insertApp)) {
        statement.setString(1, app.getName());
        statement.setString(2, app.getDescription());
        statement.setDouble(3, app.getVersion());
        statement.setDouble(4, 0.0);
        statement.setInt(5, 0);
        statement.setInt(6, app.getAuthor().getUserId());

        final ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
          final int appId = resultSet.getInt("id");
          if (app.getFeatures() != null && !app.getFeatures().isEmpty()) {
            try (final PreparedStatement featuresStatement =
                connection.prepareStatement(insertFeatures)) {
              for (String feature : app.getFeatures()) {
                featuresStatement.setInt(1, appId);
                featuresStatement.setString(2, feature.trim());
                featuresStatement.addBatch();
              }
              featuresStatement.executeBatch();
            }
          }

          System.out.println("App Saved Successfully with ID: " + appId);
        }
      }
    } catch (final SQLException exception) {
      exception.printStackTrace();
    }
  }

  /**
   * Finds an App by its unique ID.
   *
   * <p>This method performs a JOIN with the Author table to retrieve author details and a separate
   * query to fetch features.
   *
   * @param id The unique identifier of the App.
   * @return The {@link App} object if found, otherwise null.
   */
  @Override
  public App findById(final int id) {

    final String findApp = "SELECT a.*, u.username, u.role FROM app a "
            + "JOIN users u ON a.author_id = u.id "
            + "WHERE a.id = ?";

    try (final Connection connection = dataSource.getConnection();
        final PreparedStatement statement = connection.prepareStatement(findApp)) {
      statement.setInt(1, id);
      final ResultSet resultSet = statement.executeQuery();

      if (resultSet.next()) {

        final List<String> features = getAppFeatures(connection, id);
        final User author =
            new User(
                resultSet.getInt("author_id"),
                resultSet.getString("username"),
                null,
                null,
                0,
                resultSet.getString("role"));

        return new App(
            resultSet.getInt("id"),
            resultSet.getString("name"),
            author,
            resultSet.getString("description"),
            resultSet.getDouble("version"),
            features,
            resultSet.getDouble("rating"),
            resultSet.getInt("installed_count"));
      }
    } catch (final SQLException exception) {
      exception.printStackTrace();
    }

    return null;
  }

  /**
   * Updates an existing App's basic details and features.
   *
   * @param app The App object containing updated information.
   */
  @Override
  public void update(final App app) {
    final String updateQuery = "UPDATE app SET name=?, description=?, version=? WHERE id=?";

    try (final Connection connection = dataSource.getConnection()) {

      try (final PreparedStatement statement = connection.prepareStatement(updateQuery)) {
        statement.setString(1, app.getName());
        statement.setString(2, app.getDescription());
        statement.setDouble(3, app.getVersion());
        statement.setInt(4, app.getAppId());

        final int updateCount = statement.executeUpdate();
        if (updateCount > 0) {

          updateFeatures(connection, app.getAppId(), app.getFeatures());
          System.out.println("App & Features Updated Successfully.");
        } else {
          System.out.println("Update Failed: App ID not found.");
        }
      }
    } catch (final SQLException exception) {
      exception.printStackTrace();
    }
  }

  /**
   * Deletes an App by its ID.
   *
   * @param id The unique identifier of the App to delete.
   * @return true if the deletion was successful, false otherwise.
   */
  @Override
  public boolean delete(final int id) {
    final String deleteQuery = "DELETE FROM app WHERE id = ?";
    try (final Connection connection = dataSource.getConnection();
        final PreparedStatement statement = connection.prepareStatement(deleteQuery)) {

      statement.setInt(1, id);
      final int updateCount = statement.executeUpdate();
      return updateCount > 0;
    } catch (final SQLException exception) {
      exception.printStackTrace();
    }

    return false;
  }

  /**
   * Retrieves all Apps from the database.
   *
   * @return A collection of all installed {@link App} objects.
   */
  @Override
  public Collection<App> getAll() {
    final Collection<App> apps = new ArrayList<>();
    final String getAllQuery = "SELECT a.*, u.username, u.role FROM app a "
        + "JOIN users u ON a.author_id = u.id";

    try (final Connection connection = dataSource.getConnection();
        final Statement statement = connection.createStatement();
        final ResultSet resultSet = statement.executeQuery(getAllQuery)) {

      while (resultSet.next()) {
        int appId = resultSet.getInt("id");
        List<String> features = getAppFeatures(connection, appId);
        User author =
            new User(
                resultSet.getInt("author_id"),
                resultSet.getString("Username"),
                null,
                null,
                0,
                resultSet.getString("role"));

        apps.add(
            new App(
                appId,
                resultSet.getString("name"),
                author,
                resultSet.getString("description"),
                resultSet.getDouble("version"),
                features,
                resultSet.getDouble("rating"),
                resultSet.getInt("installed_count")));
      }
    } catch (final SQLException exception) {
      exception.printStackTrace();
    }

    return apps;
  }

  /**
   * Adds a user review and updates the App's average rating.
   *
   * <p>This method executes two operations: 1. Inserts the review into the 'reviews' table. 2.
   * Recalculates and updates the rating in the 'app' table.
   *
   * @param review The Review object containing user feedback.
   */
  @Override
  public void addReview(final Review review) {
    final String insertReview =
        "INSERT INTO reviews (user_id,app_id, rating, comment) VALUES (?, ?, ?, ?)";

    try (final Connection connection = dataSource.getConnection()) {

      try (final PreparedStatement statement = connection.prepareStatement(insertReview)) {
        statement.setInt(1, review.getUserId());
        statement.setInt(2, review.getAppId());
        statement.setDouble(3, review.getRating());
        statement.setString(4, review.getComment());
        statement.executeUpdate();
      }

      updateRating(connection, review.getAppId());
      System.out.println("Review added and App Rating updated!");
    } catch (final SQLException exception) {
      exception.printStackTrace();
    }
  }

  /** Fetches the list of feature strings associated with an App ID. */
  private List<String> getAppFeatures(final Connection connection, final int appId)
      throws SQLException {
    final List<String> features = new ArrayList<>();
    String featuresQuery = "SELECT features FROM features WHERE app_id = ?";
    try (PreparedStatement statement = connection.prepareStatement(featuresQuery)) {
      statement.setInt(1, appId);
      ResultSet resultSet = statement.executeQuery();
      while (resultSet.next()) {
        features.add(resultSet.getString("features"));
      }
    }

    return features;
  }

  /**
   * Updates features for an App by deleting old ones and inserting new ones.
   */
  private void updateFeatures(
      final Connection connection, final int appId, final List<String> newFeatures)
      throws SQLException {
    final String deleteFeatures = "DELETE FROM features WHERE app_id = ?";
    try (final PreparedStatement statement = connection.prepareStatement(deleteFeatures)) {
      statement.setInt(1, appId);
      statement.executeUpdate();
    }

    if (newFeatures != null && !newFeatures.isEmpty()) {
      final String insertFeatures = "INSERT INTO features (app_id, features) VALUES (?, ?)";
      try (PreparedStatement statement = connection.prepareStatement(insertFeatures)) {
        for (String features : newFeatures) {
          statement.setInt(1, appId);
          statement.setString(2, features.trim());
          statement.addBatch();
        }
        statement.executeBatch();
      }
    }
  }

  /**
   * Calculates the average rating from the reviews table and updates the app table.
   */
  private void updateRating(final Connection connection, final int appId) throws SQLException {
    final String updateReviewsTable = "SELECT AVG(rating) FROM reviews WHERE app_id = ?";
    double rating = 0.0;
    try (final PreparedStatement statement = connection.prepareStatement(updateReviewsTable)) {
      statement.setInt(1, appId);
      final ResultSet rs = statement.executeQuery();
      if (rs.next()) rating = rs.getDouble(1);
    }

    final String updateAppTable = "UPDATE app SET rating = ? WHERE id = ?";
    try (final PreparedStatement ps = connection.prepareStatement(updateAppTable)) {
      ps.setDouble(1, rating);
      ps.setInt(2, appId);
      ps.executeUpdate();
    }
  }
}

