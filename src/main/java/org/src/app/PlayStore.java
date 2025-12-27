package org.src.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Bootstraps the PlayStore application and manages the user authentication loop.
 * <p>
 * This class initializes the core services and handles the initial sign-up and
 * login flow before control to the application menu.
 */
@SpringBootApplication
@ComponentScan(basePackages = "org.src")
public class PlayStore {
    public static void main(String[] args) {
        SpringApplication.run(PlayStore.class, args);
    }
}

