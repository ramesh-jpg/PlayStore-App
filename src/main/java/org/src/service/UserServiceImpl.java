package org.src.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.src.model.User;
import org.src.repository.UserRepository;
import org.src.repository.UserRepositoryImpl;
import org.src.util.Input;

/**
 * Manages user authentication including sign-up and sign-in processes.
 * <p>
 * This class implements the {@link UserService} interface and currently uses
 * an in-memory Map to store user credentials.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    /**
     * Registers a new user with a username and password.
     *
     * Username must start with a letter and contain valid characters.
     * Username must not already exist.
     * Password must not be the same as the username.
     * Password must be at least 6 characters long.
     */

    @Override
    public void signUp(User user) {

        if (!user.getUsername().matches("[A-Za-z][A-Za-z0-9@#$%^&+=._-]*")) {
            throw new RuntimeException("Invalid Username.");
        }

        if (userRepository.getByUsername(user.getUsername())!= null) {
            throw new RuntimeException("Username Already Exist.");
        }

        if (user.getPassword().equals(user.getUsername())) {
            throw new RuntimeException("Password Can't be Same as Username.");
        }

        if (user.getPassword().length() < 6) {
            throw new RuntimeException("Password must be at least 6 characters.");
        }
        try{
            userRepository.save(user);
        }catch(RuntimeException exception){
            String errorMessage = exception.getMessage();
            if (errorMessage.contains("users_phone_key")) {
                System.out.println(" Phone number already registered!");
            } else if (errorMessage.contains("users_email_key")) {
                System.out.println("Email already registered!");
            } else if (errorMessage.contains("users_username_key")) {
                System.out.println("Username already taken!");
            } else {
                System.out.println("Signup Failed: " +errorMessage);
                exception.printStackTrace();
            }
        }

    }

    /**
     * Authenticates a user by verifying the username and password.
     *
     * @return true if login is successful, @return false otherwise.
     */

    @Override
    public User signIn(String username,String password) {
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
