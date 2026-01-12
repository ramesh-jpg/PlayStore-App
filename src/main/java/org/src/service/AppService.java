package org.src.service;

import java.util.Collection;
import org.src.model.App;
import org.src.model.Review;

/**
 * Defines business logic and administrative operations for managing applications.
 *
 * <p>This interface handles core operations such as creating new apps, updating existing details,
 * installing/uninstalling apps, and managing reviews.
 */
public interface AppService {

  /**
   * Creates a new application and persists it to the storage.
   *
   * <p>Implementations should validate that the App name or ID is unique.
   *
   * @param app the {@link App} object containing details to be saved
   */
  void createApp(App app);

  /**
   * Updates the details of an existing application.
   *
   * <p>Implementations should verify that the requester is the original author before allowing
   * updates.
   *
   * @param app the {@link App} object containing updated information
   */
  void updateApp(App app);

  /**
   * Permanently removes an application from the system.
   *
   * <p>Requires author verification before deletion to prevent unauthorized removal.
   *
   * @param appId the unique identifier of the app to delete
   * @param authorId the ID of the author requesting the deletion
   */
  void deleteApp(int appId, int authorId);

  /**
   * Retrieves all applications available in the PlayStore.
   *
   * @return a collection of {@link App} objects
   */
  Collection<App> listApps();

  /**
   * Installs an application on the user's device.
   *
   * <p>Implementation should verify if the app exists and is not already installed before updating
   * the status and installation count.
   *
   * @param userId the ID of the user installing the app
   * @param appId the ID of the app to be installed
   */
  void installApp(int userId, int appId);

  /**
   * Removes an installed application from the user's device.
   *
   * <p>Implementation should ensure the app is currently installed before attempting to remove it.
   *
   * @param userId the ID of the user uninstalling the app
   * @param appId the ID of the app to be uninstalled
   */
  void unInstallApp(int userId, int appId);

  /**
   * Submits a user review for an application.
   *
   * <p>This allows users to rate and comment on apps they have used.
   *
   * @param review the {@link Review} object containing the rating and comment
   */
  void writeReview(Review review);
}
