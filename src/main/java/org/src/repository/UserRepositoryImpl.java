package org.src.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.src.model.User;

/**
 * SQL implementation of the {@link UserRepository} interface.
 *
 * <p>This class uses JDBC to connect to the 'users' table in the database. It handles SQL queries
 * for Sign-up and Sign-in operations.
 */
@Repository
public class UserRepositoryImpl implements UserRepository {

  private final DataSource dataSource;

  @Autowired
  public UserRepositoryImpl(final DataSource dataSource) {
    this.dataSource = dataSource;
  }

  /**
   * Inserts a new user record into the database.
   *
   * <p>SQL: {@code INSERT INTO users (username, password, email, phone, role) VALUES ...}
   *
   * @param user the {@link User} object containing details to be saved
   * @throws RuntimeException if a database access error occurs
   */
  @Override
  public void save(final User user) {
    try (final Connection connection = dataSource.getConnection();
        final PreparedStatement statement =
            connection.prepareStatement(
                "INSERT INTO users(username,password,email,phone,role) VALUES (?,?,?,?,?)")) {
      statement.setString(1, user.getUsername());
      statement.setString(2, user.getPassword());
      statement.setString(3, user.getEmail());
      statement.setLong(4, user.getPhone());
      statement.setString(5, user.getRole());
      statement.executeUpdate();
    } catch (SQLException exception) {
      throw new RuntimeException(exception.getMessage());
    }
  }

  /**
   * Retrieves a user record based on the username.
   *
   * <p>SQL: {@code SELECT * FROM users WHERE username = ?}
   *
   * @param username the username to search for
   * @return the {@link User} object if found, otherwise {@code null}
   */
  @Override
  public User getByUsername(final String username) {
    try (final Connection connection = dataSource.getConnection();
        final PreparedStatement statement =
            connection.prepareStatement("SELECT * FROM users WHERE username=?")) {
      statement.setString(1, username);
      final ResultSet result = statement.executeQuery();
      if (result.next())
        return new User(
            result.getInt(1),
            result.getString(2),
            result.getString(3),
            result.getString(4),
            result.getLong(5),
            result.getString(6));
    } catch (final SQLException exception) {
      exception.printStackTrace();
    }

    return null;
  }
}
