package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utility class to manage Database Connections.
 * <p>
 * This class provides a to establish a connection with the PostgreSQL database.
 * It uses {@link DriverManager} to fetch the connection using the provided credentials.
 */
public final class ConnectionUtil {

    // Database Configuration Constants
    // NOTE: Ensure these credentials match your local PostgreSQL setup
    public static final String URL = "jdbc:postgresql://localhost:5432/PlayStore";
    public static final String USER = "postgres";
    public static final String PASSWORD = "Ramesh@123";

    /**
     * Establishes and returns a connection to the Database.
     *
     * @return A valid {@link Connection} object to perform SQL operations.
     * @throws RuntimeException if the connection fails (wraps the original {@link SQLException}).
     */
    public static Connection getConnection(){
        try{
            return DriverManager.getConnection(URL,USER,PASSWORD);
        } catch (final SQLException exception) {
            throw new RuntimeException("DataBase Connection Failed "+ exception.getMessage());
        }

    }
}
