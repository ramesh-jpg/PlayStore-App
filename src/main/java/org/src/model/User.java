package org.src.model;

import java.util.Objects;

/**
 * Represents a registered user of the PlayStore application.
 *
 * <p>This model contains authentication details (username, password), contact information, and the
 * user's role (e.g., USER or AUTHOR).
 */
public final class User {

  private int userId;
  private String username;
  private String password;
  private String email;
  private long phone;
  private String role;

  /** Default constructor required for JSON deserialization. */
  public User() {}

  /**
   * Constructs a new User with full details.
   *
   * @param userId The unique identifier for the user.
   * @param username The unique login username.
   * @param password The user's password.
   * @param email The user's email address.
   * @param phone The user's phone number.
   * @param role The role of the user.
   */
  public User(
      final int userId,
      final String username,
      final String password,
      final String email,
      final long phone,
      final String role) {
    this.userId = userId;
    this.username = username;
    this.password = password;
    this.email = email;
    this.phone = phone;
    this.role = role;
  }

  /**
   * Helper constructor for login or user creation.
   *
   * @param username The login username.
   * @param password The login password.
   */
  public User(final String username, final String password) {
    this(0, username, password, null, 0, "USER");
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public long getPhone() {
    return phone;
  }

  public void setPhone(long phone) {
    this.phone = phone;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  @Override
  public String toString() {
    return "User{id=" + userId + ", username='" + username + "', role='" + role + "'}";
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
