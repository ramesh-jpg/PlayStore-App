package repository;

import model.App;
import model.Author;
import model.Review;
import util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Implementation of the {@link AppRepository} interface for database operations.
 * <p>
 * This class handles CRUD operations for Apps, including managing related entities
 * like Authors, Features, and Reviews. It uses JDBC for database connectivity.
 */
public class AppRepositoryImpl implements AppRepository {

    /**
     * Saves a new App to the database along with its features and author.
     * <p>
     * If the author does not exist, a new author is created.
     *Insert the features in app
     * @param app The App object containing details to be saved.
     */
    @Override
    public void save(final App app) {
        final String insertApp = "INSERT INTO app (name, description, version, rating, installed_count, author_id) VALUES (?, ?, ?, ?, ?, ?) RETURNING app_id";
        final String insertFeatures = "INSERT INTO features (app_id, features) VALUES (?, ?)";

        try (final Connection connection = ConnectionUtil.getConnection()) {

            final int authorId = getAuthorId(connection, app.getAuthorName());

            try (final PreparedStatement statement = connection.prepareStatement(insertApp)) {
                statement.setString(1, app.getName());
                statement.setString(2, app.getDescription());
                statement.setDouble(3, app.getVersion());
                statement.setDouble(4, 0.0);
                statement.setInt(5, 0);
                statement.setInt(6, authorId);

               final ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                   final int appId = resultSet.getInt(1);
                    if (app.getFeatures() != null && !app.getFeatures().isEmpty()) {
                        try (final PreparedStatement featuresStatement = connection.prepareStatement(insertFeatures)) {
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
     * <p>
     * This method performs a JOIN with the Author table to retrieve author details
     * and a separate query to fetch features.
     *
     * @param id The unique identifier of the App.
     * @return The {@link App} object if found, otherwise null.
     */
    @Override
    public App findById(final int id) {

        final String findApp = "SELECT a.app_id, a.name, au.author_name, a.description, a.version, a.rating, a.installed_count " +
                "FROM app a " +
                "JOIN author au ON a.author_id = au.author_id " +
                "WHERE a.app_id = ?";

        try (final Connection connection = ConnectionUtil.getConnection();
             final PreparedStatement statement = connection.prepareStatement(findApp)) {
            statement.setInt(1, id);
            final ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

               final List<String> features = getappFeatures(connection, id);
               final Author author = new Author(1, resultSet.getString("author_name"));

                return new App(
                        resultSet.getInt("app_id"),
                        resultSet.getString("name"),
                        author,
                        resultSet.getString("description"),
                        resultSet.getDouble("version"),
                        features,
                        resultSet.getDouble("rating"),
                        resultSet.getInt("installed_count")
                );
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
        final String updateQuery = "UPDATE app SET name=?, description=?, version=? WHERE app_id=?";

        try (final Connection connection = ConnectionUtil.getConnection()) {

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
        final String deleteQuery = "DELETE FROM app WHERE app_id = ?";
        try (final Connection connection = ConnectionUtil.getConnection();
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
        final String getAllQuery = "SELECT a.app_id, a.name, au.author_name, a.description, a.version, a.rating, a.installed_count " +
                "FROM app a " +
                "JOIN author au ON a.author_id = au.author_id";

        try (final Connection connection = ConnectionUtil.getConnection();
             final Statement statement = connection.createStatement();
             final ResultSet resultSet = statement.executeQuery(getAllQuery)) {

            while (resultSet.next()) {
                int appId = resultSet.getInt("app_id");
                List<String> features = getappFeatures(connection, appId);
                Author author = new Author(1, resultSet.getString("author_name"));

                apps.add(new App(
                        appId,
                        resultSet.getString("name"),
                        author,
                        resultSet.getString("description"),
                        resultSet.getDouble("version"),
                        features,
                        resultSet.getDouble("rating"),
                        resultSet.getInt("installed_count")
                ));
            }
        } catch (final SQLException exception) {
            exception.printStackTrace();
        }
        return apps;
    }

    /**
     * Adds a user review and updates the App's average rating.
     * <p>
     * This method executes two operations:
     * 1. Inserts the review into the 'reviews' table.
     * 2. Recalculates and updates the rating in the 'app' table.
     *
     * @param review The Review object containing user feedback.
     */
    @Override
    public void addReview(final Review review) {
        final String insertReview = "INSERT INTO reviews (user_id, app_id, rating, comment) VALUES (?, ?, ?, ?)";

        try (final Connection connection = ConnectionUtil.getConnection()) {

            try (final PreparedStatement statement = connection.prepareStatement(insertReview)) {
                statement.setInt(1, review.getUserId());
                statement.setString(2,review.getUserName());
                statement.setInt(3, review.getAppId());
                statement.setDouble(4, review.getRating());
                statement.setString(5, review.getComment());
                statement.executeUpdate();
            }
            updateRating(connection, review.getAppId());
            System.out.println("Review added and App Rating updated!");

        } catch (final SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Retrieves the Author ID for a given name, creating a new Author if none exists.
     */
    private int getAuthorId(final Connection connection, final String authorName) throws SQLException {

        final String authorQuery = "SELECT author_id FROM author WHERE author_name = ?";
        try (final PreparedStatement statement = connection.prepareStatement(authorQuery)) {
            statement.setString(1, authorName);
            final ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("author_id");
            }
        }

        final String insertAuthor = "INSERT INTO author (author_name) VALUES (?) RETURNING author_id";
        try (final PreparedStatement statement = connection.prepareStatement(insertAuthor)) {
            statement.setString(1, authorName);
            final ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        }
        throw new SQLException("Could not handle Author creation");
    }

    /**
     * Fetches the list of feature strings associated with an App ID.
     */
    private List<String> getappFeatures(final Connection connection, final int appId) throws SQLException {
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
    private void updateFeatures(final Connection connection, final int appId, final List<String> newFeatures) throws SQLException {
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
                    statement.addBatch(); // Performance optimization
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

        final String updateAppTable = "UPDATE app SET rating = ? WHERE app_id = ?";
        try (final PreparedStatement ps = connection.prepareStatement(updateAppTable)) {
            ps.setDouble(1, rating);
            ps.setInt(2, appId);
            ps.executeUpdate();
        }
    }
}