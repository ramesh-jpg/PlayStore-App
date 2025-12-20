package repository;

import model.User;
import util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * SQL implementation of the {@link UserRepository} interface.
 * <p>
 * This class uses JDBC to connect to the 'users' table in the database.
 * It handles SQL queries for Sign-up and Sign-in operations.
 */
public class UserRepositoryImpl implements UserRepository{

    /**
     * Inserts a new user record into the database.
     * <p>
     * SQL: INSERT INTO users (username, password) VALUES (?, ?)
     */
    @Override
    public void save(final User user) {
        try (final Connection connection = ConnectionUtil.getConnection();//connectionutil usage
             final PreparedStatement statement = connection.prepareStatement("INSERT INTO users(username,password) VALUES (?, ?)")) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.executeUpdate();
        } catch (SQLException exception) { exception.printStackTrace(); }
    }

    /**
     * Retrieves a user record based on the username.
     * <p>
     * SQL: SELECT * FROM users WHERE username = ?
     */
    @Override
    public User getByUsername(final String username) {
        try (final Connection connection = ConnectionUtil.getConnection();
             final PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE username=?")) {
            statement.setString(1, username);
            final ResultSet result = statement.executeQuery();
            if (result.next()) return new User(result.getInt(1), result.getString(2), result.getString(3));;
        } catch (final SQLException exception) { exception.printStackTrace(); }
        return null;
    }
}
