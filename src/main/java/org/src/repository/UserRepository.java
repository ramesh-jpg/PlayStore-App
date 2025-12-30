package org.src.repository;

import org.src.model.User;

/**
 * Defines the contract for User data management.
 *
 * <p>This interface handles the storage for Users. It provides methods to register new users and
 * retrieve existing ones for signin.
 */
public interface UserRepository {

  /**
   * Persists a new user to the storage (Database).
   *
   * @param user the {@link User} object containing username, password, and other details
   */
  void save(User user);

  /**
   * Finds a User by their unique username.
   *
   * <p>This method is primarily used during the login process to verify credentials.
   *
   * @param username the username to search for
   * @return the {@link User} object if found, or {@code null} if no match exists
   */
  User getByUsername(String username);
}
