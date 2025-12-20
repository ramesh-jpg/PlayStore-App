package model;

import java.util.Objects;
/**
 * Represents a registered user of the PlayStore application.
 * <p>
 * This model contains authentication details (username, password)
 */

public final class User {

    private final int userId;
    private String username;
    private String password;

    /**
     * Constructs a new User with full details.
     *
     * @param userId      The unique identifier for the user.
     * @param username    The unique login username.
     * @param password    The user's password (stored as plain text/hash).
     */
    public User(final int userId, final String username,final String password) {
        this.userId = userId;
        this.username = username;
        this.password = password;
    }

    public User(final String username, final String password) {
        this(0, username, password);
    }

    public int getUserId() { return userId; }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "User{id=" + userId + ", username='" + username + "'}";
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        User user = (User) object;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(username);
    }
}
