package org.src.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.src.model.User;
import org.src.service.UserService;

/**
 * REST Controller for managing user accounts and authentication.
 *
 * <p>This controller provides endpoints for user registration (sign-up) and authentication
 * (sign-in). It delegates business logic to the {@link UserService}.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {
  private final UserService userService;

  /**
   * Constructs the UserController with the required service dependency.
   *
   * <p>Constructor injection ensures that the controller is initialized with a valid {@link
   * UserService}
   *
   * @param userService the service responsible for user management logic
   */
  @Autowired
  public UserController(final UserService userService) {
    this.userService = userService;
  }

  /**
   * Registers a new user in the system.
   *
   * <p>This endpoint accepts a user object, validates it via the service layer, and persists it to
   * the database.
   *
   * @param user the user object containing details like username, password, and role
   * @return a {@link ResponseEntity} with a success message if created, or an error message if the
   *     request is invalid
   */
  @PostMapping("/signup")
  public ResponseEntity<String> signUp(@RequestBody final User user) {

    try {
      userService.signUp(user);
      return ResponseEntity.ok("Signup Successful! ");
    } catch (RuntimeException exception) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(" Error: " + exception.getMessage());
    }
  }

  /**
   * Authenticates a user based on provided credentials.
   *
   * <p>This endpoint checks the username and password. If valid, it returns the full user details
   *
   * @param loginDetails a user object containing only the username and password for login
   * @return a {@link ResponseEntity} containing the {@link User} object if successful, or an
   *     "Unauthorized" error message if authentication fails
   */
  @PostMapping("/login")
  public ResponseEntity<?> signIn(@RequestBody final User loginDetails) {

    try {
      final User user = userService.signIn(loginDetails.getUsername(), loginDetails.getPassword());
      return ResponseEntity.ok(user);
    } catch (final RuntimeException exception) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .body(" Login Failed: " + exception.getMessage());
    }
  }
}
