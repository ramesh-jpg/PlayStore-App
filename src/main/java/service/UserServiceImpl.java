package service;

import model.User;
import repository.UserRepository;
import repository.UserRepositoryImpl;
import util.Input;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages user authentication including sign-up and sign-in processes.
 * <p>
 * This class implements the {@link UserService} interface and currently uses
 * an in-memory Map to store user credentials.
 */

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository = new UserRepositoryImpl();

    /**
     * Helper method to display a prompt and read a string input.
     *
     * @param message The text to display to the user.
     * @return The string input entered by the user.
     */

    private String promptInput(final String message){
        return Input.readString(message);
    }

    /**
     * Registers a new user with a username and password.
     *
     * Username must start with a letter and contain valid characters.
     * Username must not already exist.
     * Password must not be the same as the username.
     * Password must be at least 6 characters long.
     */

    @Override
    public void signUp() {
        final String userName = promptInput("UserName: ");

        if (!userName.matches("[A-Za-z][A-Za-z0-9@#$%^&+=._-]*")) {
            System.out.println("Invalid Username.");
            return;
        }

        if (userRepository.getByUsername(userName)!= null) {
            System.out.println("Username Already Exist.");
            return;
        }

        final String password = promptInput("Password: ");
        if (password.equals(userName)) {
            System.out.println("Password Can't be Same as Username.");
            return;
        }

        if (password.length() < 6) {
            System.out.println("Password must be at least 6 characters.");
            return;
        }

        final User newuser = new User(userName, password);
        userRepository.save(newuser);
        System.out.println("Signup complete.");
    }

    /**
     * Authenticates a user by verifying the username and password.
     *
     * @return true if login is successful, @return false otherwise.
     */

    @Override
    public User signIn() {
        final String userName = promptInput("UserName: ");
        final User user = userRepository.getByUsername(userName);

        if (user == null) {
            System.out.println("UserName NotFound. ");
            return null;
        }
        final String password = promptInput("PassWord: ");

        if (user.getPassword().equals(password)) {
            System.out.println("Login SuccessFully. ");
            return user;
        } else {
            System.out.println("Wrong PassWord. ");
        }
        return null;
    }
}
