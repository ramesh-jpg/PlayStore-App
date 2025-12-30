package org.src.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.src.model.User;
import org.src.repository.UserRepository;

/**
 * Manages user authentication including sign-up and sign-in processes.
 *
 * <p>This class implements the {@link UserService} interface to handle user registration
 * validations and credential verification.
 */
@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  /**
   * Constructs the UserServiceImpl with the required repository dependency.
   *
   * <p>Constructor injection is used here to ensure the service has a valid repository instance,
   * making it easier to test and avoiding null pointers.
   *
   * @param userRepository the repository for User data operations
   */
  @Autowired
  public UserServiceImpl(final UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  /**
   * Registers a new user after validating inputs and checking for duplicates.
   *
   * <p>Validation rules:
   *
   * <ul>
   *   <li>Username must start with a letter and contain valid characters.
   *   <li>Username must not already exist.
   *   <li>Password must not be the same as the username.
   *   <li>Password must be at least 6 characters long.
   * </ul>
   *
   * @param user the user object containing registration details
   * @throws RuntimeException if validation fails or database constraints are violated
   */
  @Override
  public void signUp(final User user) {

    if (!user.getUsername().matches("[A-Za-z][A-Za-z0-9@#$%^&+=._-]*")) {
      throw new RuntimeException("Invalid Username.");
    }

    if (userRepository.getByUsername(user.getUsername()) != null) {
      throw new RuntimeException("Username Already Exist.");
    }

    if (user.getPassword().equals(user.getUsername())) {
      throw new RuntimeException("Password Can't be Same as Username.");
    }

    if (user.getPassword().length() < 6) {
      throw new RuntimeException("Password must be at least 6 characters.");
    }
    try {
      userRepository.save(user);
    } catch (RuntimeException exception) {
      String errorMessage = exception.getMessage();
      if (errorMessage.contains("users_phone_key")) {
        System.out.println(" Phone number already registered!");
      } else if (errorMessage.contains("users_email_key")) {
        System.out.println("Email already registered!");
      } else if (errorMessage.contains("users_username_key")) {
        System.out.println("Username already taken!");
      } else {
        System.out.println("Signup Failed: " + errorMessage);
        exception.printStackTrace();
      }
    }
  }

  /**
   * Authenticates a user by verifying the username and password.
   *
   * @param username the username provided during login
   * @param password the password provided during login
   * @return the authenticated {@link User} object
   * @throws RuntimeException if the user is not found or password does not match
   */
  @Override
  public User signIn(final String username, final String password) {
    final User user = userRepository.getByUsername(username);

    if (user == null) {
      throw new RuntimeException("UserName NotFound. ");
    }

    if (user.getPassword().equals(password)) {
      return user;
    } else {
      throw new RuntimeException("Wrong PassWord. ");
    }
  }
}
