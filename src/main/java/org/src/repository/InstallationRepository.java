package org.src.repository;

import java.util.Collection;
import org.src.model.App;

/**
 * Repository interface for managing application installations.
 *
 * <p>This interface defines operations to track which user has installed which app, allowing for
 * installation, uninstallation, and checking status.
 */
public interface InstallationRepository {

  /**
   * Records a new installation of an app for a user.
   *
   * @param userId the unique identifier of the user installing the app
   * @param appId the unique identifier of the app being installed
   * @return {@code true} if the installation was recorded successfully, {@code false} otherwise
   */
  boolean installed(int userId, int appId);

  /**
   * Removes an installation record (Uninstalls the app).
   *
   * @param userId the unique identifier of the user
   * @param appId the unique identifier of the app to be uninstalled
   * @return {@code true} if the uninstallation was successful, {@code false} otherwise
   */
  boolean unInstalled(int userId, int appId);

  /**
   * Checks if a specific app is currently installed by a user.
   *
   * @param userId the unique identifier of the user
   * @param appId the unique identifier of the app
   * @return {@code true} if the app is installed, {@code false} otherwise
   */
  boolean isInstalled(int userId, int appId);

  /**
   * Retrieves a list of all apps installed by a specific user.
   *
   * @param userId the unique identifier of the user
   * @return a collection of {@link App} objects representing the user's installed apps
   */
  Collection<App> getInstalledApps(int userId);
}
