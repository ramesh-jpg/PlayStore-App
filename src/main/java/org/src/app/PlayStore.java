package org.src.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * The entry point for the PlayStore Spring Boot application.
 *
 * <p>This class using Spring Boot's autoconfiguration. It also component scanning to detect beans
 * within the {@code org.src} package.
 *
 * <p>Annotated with:
 *
 * <ul>
 *   <li>{@link SpringBootApplication} - Enables auto-configuration and configuration support.
 *   <li>{@link ComponentScan} - Scans the specified base packages for components.
 * </ul>
 */
@SpringBootApplication
@ComponentScan(basePackages = "org.src")
public class PlayStore {
  public static void main(String[] args) {
    SpringApplication.run(PlayStore.class, args);
  }
}
