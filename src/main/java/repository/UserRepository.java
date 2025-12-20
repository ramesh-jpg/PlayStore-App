package repository;

import model.User;

/**
 * Defines the contract for User data management.
 * <p>
 * This interface  the storage for Users.
 * It provides methods to register new users and retrieve existing ones for signin.
 */
public interface UserRepository {

       /**
        * Persists a new user to the storage (Database).
        *
        * @param user The {@link User} object containing username and password.
        */
       void save(User user);

       /**
        * Finds a User by their unique username.
        * Used mainly during the Login process to verify credentials.
        *
        * @param username The username to search for.
        * @return The {@link User} object if found, otherwise returns {@code null}.
        */
       User getByUsername(String username);
}

