package org.src.controller;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.src.model.App;
import org.src.model.Review;
import org.src.service.AppService;

/**
 * REST Controller for managing applications in the PlayStore.
 *
 * <p>This controller provides endpoints to create, update, delete, install, uninstall, and review
 * applications. It interacts with the {@link AppService} to perform business logic.
 */
@RestController
@RequestMapping("/api/apps")
public class AppController {

  private final AppService appService;

  /**
   * Constructs the AppController with the required service dependency.
   *
   * <p>Using constructor injection ensures that the controller initialized with valid dependencies
   *
   * @param appService the business logic service for application management
   */
  @Autowired
  public AppController(final AppService appService) {
    this.appService = appService;
  }

  /**
   * Creates a new application in the PlayStore.
   *
   * @param app the application object containing details like name, version, and author
   * @return the {@link ResponseEntity} with a success message or error details
   */
  @PostMapping
  public ResponseEntity<String> createApp(@RequestBody final App app) {

    try {
      appService.createApp(app);
      return ResponseEntity.ok("App Created Successfully!");
    } catch (RuntimeException exception) {
      return ResponseEntity.badRequest().body("Error: " + exception.getMessage());
    }
  }

  /**
   * Retrieves a list of all available applications.
   *
   * @return the collection of {@link App} objects representing all apps in the PlayStore
   */
  @GetMapping
  public ResponseEntity<Collection<App>> listApps() {
    return ResponseEntity.ok(appService.listApps());
  }

  /**
   * Updates an existing application's details.
   *
   * @param app the application object with updated information
   * @return the success message if updated, otherwise an error message
   */
  @PutMapping
  public ResponseEntity<String> updateApp(@RequestBody final App app) {

    try {
      appService.updateApp(app);
      return ResponseEntity.ok("App Updated Successfully!");
    } catch (RuntimeException exception) {
      return ResponseEntity.badRequest().body("Error: " + exception.getMessage());
    }
  }

  /**
   * Deletes an application from the PlayStore.
   *
   * <p>Requires both the App ID and Author ID to ensure only Author can delete it.
   *
   * @param appId the unique identifier of the app to be deleted
   * @param authorId the unique identifier of the author performing the deletion
   * @return the success message or error if validation fails
   */
  @DeleteMapping("/{appId}")
  public ResponseEntity<String> deleteApp(
      @PathVariable final int appId, @RequestParam final int authorId) {

    try {
      appService.deleteApp(appId, authorId);
      return ResponseEntity.ok("App Deleted Successfully!");
    } catch (RuntimeException exception) {
      return ResponseEntity.badRequest().body("Error: " + exception.getMessage());
    }
  }

  /**
   * Installs an application for a specific user.
   *
   * @param userId the ID of the user installing the app
   * @param appId the ID of the app being installed
   * @return the success message upon installation
   */
  @PostMapping("/install")
  public ResponseEntity<String> installApp(
      @RequestParam final int userId, @RequestParam final int appId) {

    try {
      appService.installApp(userId, appId);
      return ResponseEntity.ok("App Installed Successfully!");
    } catch (RuntimeException exception) {
      return ResponseEntity.badRequest().body("Error: " + exception.getMessage());
    }
  }

  /**
   * Uninstalls an application for a specific user.
   *
   * @param userId the ID of the user uninstalling the app
   * @param appId the ID of the app being uninstalled
   * @return the success message upon uninstallation
   */
  @PostMapping("/uninstall")
  public ResponseEntity<String> unInstallApp(
      @RequestParam final int userId, @RequestParam final int appId) {

    try {
      appService.unInstallApp(userId, appId);
      return ResponseEntity.ok("App Uninstalled Successfully!");
    } catch (RuntimeException exception) {
      return ResponseEntity.badRequest().body("Error: " + exception.getMessage());
    }
  }

  /**
   * Submits a review and rating for an application.
   *
   * @param review the review object containing user ID, app ID, rating, and comment
   * @return the success message if the review is added
   */
  @PostMapping("/review")
  public ResponseEntity<String> writeReview(@RequestBody final Review review) {

    try {
      appService.writeReview(review);
      return ResponseEntity.ok("Review Added Successfully!");
    } catch (RuntimeException exception) {
      return ResponseEntity.badRequest().body("Error: " + exception.getMessage());
    }
  }
}
