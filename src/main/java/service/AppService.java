package service;

import model.User;

/**
 * Defines administrative operations for managing the applications.
 * <p>
 * This interface handles write-heavy operations such as creating new apps,
 * updating existing details, and removing apps from the repository.
 */
public interface AppService {

    /**
     * Creates a new application and persists it to the storage.
     * Should validate that the App ID is unique.
     */
    void createApp();

    /**
     * Updates the details of an existing application.
     * Implementations should verify that the requester is the original author.
     */
    void updateApp();

    /**
     * Permanently removes an application from the system.
     * Requires author verification before deletion.
     */
    void deleteApp();

    /**
     * Displays the full applications available in the PlayStore.
     * Generally lists details like App ID, Name,Version and Features.
     */
    void listApps();

    /**
     * Installation of an application on the users device.
     * <p>
     * Implementation should verify if the app exists and is not already installed
     * before it typically updates the app's status and installation count.
     */
    void installApp();

    /**
     * Removes an installed application from the user's device.
     * <p>
     * Implementation should ensure the app is currently installed before
     * attempting to remove it.
     */
    void unInstallApp();


    void writeReview(User signInUser);
}
