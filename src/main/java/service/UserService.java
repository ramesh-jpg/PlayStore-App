package service;

import model.User;

/**
 * Defines the contract for user authentication and account management.
 * <p>
 * This interface outlines the essential operations for user
 * such as registration (sign-up) and login (sign-in).
 */
public interface UserService {

    /**
     * Registers a new user in the system.
     * Implementations should handle username validation and password security.
     */
    void signUp();

    /**
     * Authenticates a user based on their credentials.
     * return {code true} if authentication is successful, { code false} otherwise.
     */
    User signIn();
}
