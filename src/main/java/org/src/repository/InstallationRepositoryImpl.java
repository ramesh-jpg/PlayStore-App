package org.src.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.src.model.App;
import org.src.model.User;

/**
 * Implementation of the {@link InstallationRepository} interface.
 *
 * <p>This class handles the database operations for tracking user installations. It manages the
 * relationship between Users and Apps in the 'installation' table and updates the installation
 * counts in the 'app' table.
 */
@Repository
public class InstallationRepositoryImpl implements InstallationRepository {

  private final DataSource dataSource;

  @Autowired
  public InstallationRepositoryImpl(final DataSource dataSource) {
    this.dataSource = dataSource;
  }

  /**
   * Records a new installation and increments the app's install count.
   *
   * @param userId the ID of the user installing the app
   * @param appId the ID of the app being installed
   * @return {@code true} if successful, {@code false} otherwise
   */
  @Override
  public boolean installed(final int userId, final int appId) {
    final String insertQuery = "INSERT INTO installation (user_id, app_id) VALUES (?, ?)";
    final String updateQuery = "UPDATE app SET installed_count = installed_count + 1 WHERE id = ?";

    try (Connection connection = dataSource.getConnection();
        PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
        PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {

      // Add to installation table
      insertStatement.setInt(1, userId);
      insertStatement.setInt(2, appId);
      insertStatement.executeUpdate();

      // Increment count in app table
      updateStatement.setInt(1, appId);
      updateStatement.executeUpdate();

      return true;

    } catch (final SQLException exception) {
      exception.printStackTrace();
      return false;
    }
  }

  /**
   * Removes an installation record and decrements the app's install count.
   *
   * @param userId the ID of the user uninstalling the app
   * @param appId the ID of the app being uninstalled
   * @return {@code true} if uninstallation was successful, {@code false} otherwise
   */
  @Override
  public boolean unInstalled(final int userId, final int appId) {
    final String deleteQuery = "DELETE FROM installation WHERE user_id = ? AND app_id = ?";
    final String updateQuery = "UPDATE app SET installed_count = installed_count - 1 WHERE id = ?";

    try (Connection connection = dataSource.getConnection();
        PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
        PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {

      deleteStatement.setInt(1, userId);
      deleteStatement.setInt(2, appId);
      int rowsDeleted = deleteStatement.executeUpdate();

      if (rowsDeleted > 0) {
        updateStatement.setInt(1, appId);
        updateStatement.executeUpdate();
        return true;
      } else {
        return false;
      }

    } catch (final SQLException exception) {
      exception.printStackTrace();
      return false;
    }
  }

  /**
   * Checks if a user has installed a specific app.
   *
   * @param userId the ID of the user
   * @param appId the ID of the app
   * @return {@code true} if a record exists, {@code false} otherwise
   */
  @Override
  public boolean isInstalled(final int userId, final int appId) {
    final String sql = "SELECT id FROM installation WHERE user_id = ? AND app_id = ?";

    try (Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {

      statement.setInt(1, userId);
      statement.setInt(2, appId);

      try (ResultSet resultSet = statement.executeQuery()) {
        return resultSet.next();
      }

    } catch (final SQLException exception) {
      exception.printStackTrace();
    }
    return false;
  }

  /**
   * Retrieves all apps installed by a specific user.
   *
   * <p>This method joins the 'app', 'installation', and 'users' (author) tables to construct full
   * App objects.
   *
   * @param userId the ID of the user
   * @return a collection of installed {@link App} objects
   */
  @Override
  public Collection<App> getInstalledApps(final int userId) {
    Collection<App> installedApps = new ArrayList<>();
    String installQuery =
        "SELECT a.*, u.username, u.role FROM app a "
            + "JOIN installation i ON a.id = i.app_id "
            + "JOIN users u ON a.author_id = u.id "
            + "WHERE i.user_id = ?";

    try (Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(installQuery)) {

      statement.setInt(1, userId);
      ResultSet resultSet = statement.executeQuery();

      while (resultSet.next()) {
        User author =
            new User(
                resultSet.getInt("author_id"),
                resultSet.getString("username"),
                null,
                null,
                0,
                resultSet.getString("role"));

        installedApps.add(
            new App(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                author,
                resultSet.getString("description"),
                resultSet.getDouble("version"),
                new ArrayList<>(),
                resultSet.getDouble("rating"),
                resultSet.getInt("installed_count")));
      }
    } catch (final SQLException exception) {
      exception.printStackTrace();
    }
    return installedApps;
  }
}
