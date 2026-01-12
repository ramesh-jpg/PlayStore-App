package org.src.service;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.src.model.App;
import org.src.model.Review;
import org.src.repository.AppRepository;
import org.src.repository.InstallationRepository;

/**
 * Implementation of the application management services.
 *
 * <p>This class serves as the central logic layer, implementing the {@link AppService} interface to
 * handle administrative tasks, user interactions, and validation logic.
 */
@Service
public class AppServiceImpl implements AppService {

  private final AppRepository appRepository;
  private final InstallationRepository installationRepository;

  /**
   * Constructs the AppServiceImpl with necessary repository dependencies.
   *
   * <p>Constructor injection is used here
   *
   * @param appRepository the repository for App data operations
   * @param installationRepository the repository for Installation data operations
   */
  @Autowired
  public AppServiceImpl(final AppRepository appRepository,
                        final InstallationRepository installationRepository) {
    this.appRepository = appRepository;
    this.installationRepository = installationRepository;
  }

  /**
   * Creates a new application after validating author permissions.
   *
   * @param app the app details to be saved
   * @throws RuntimeException if the author is missing or lacks the 'AUTHOR' role
   */
  @Override
  public void createApp(final App app) {
    if (app.getAuthor() == null) {
      throw new RuntimeException("Author details missing.");
    }

    if (!"AUTHOR".equalsIgnoreCase(app.getAuthor().getRole())) {
      throw new RuntimeException("Only AUTHOR can create apps.");
    }

    appRepository.save(app);
  }

  /**
   * Updates an existing application's details.
   *
   * <p>Validates that the app exists and the requester matches the original author.
   *
   * @param app the app object with updated details
   * @throws RuntimeException if the app is not found or the user is unauthorized
   */
  @Override
  public void updateApp(final App app) {
    final App existingApp = appRepository.findById(app.getAppId());

    if (existingApp == null) {
      throw new RuntimeException("App not found. ");
    }

    if (existingApp.getAuthor().getUserId() != app.getAuthor().getUserId()) {
      throw new RuntimeException(" You are not allowed Update this app.");
    }

    existingApp.setName(app.getName());
    existingApp.setFeatures(app.getFeatures());
    existingApp.setDescription(app.getDescription());
    existingApp.setVersion(app.getVersion());

    appRepository.update(existingApp);
  }

  /**
   * Deletes an application from the store.
   *
   * @param appId the ID of the app to delete
   * @param authorId the ID of the author requesting deletion
   * @throws RuntimeException if validation fails
   */
  @Override
  public void deleteApp(final int appId, final int authorId) {
    final App existingApp = appRepository.findById(appId);
    if (existingApp == null) {
      throw new RuntimeException("App not found.");
    }

    if (existingApp.getAuthor().getUserId() != authorId) {
      throw new RuntimeException("You are not allowed delete this app.");
    }

    appRepository.delete(appId);
  }

  /**
   * Lists all available applications in the repository.
   *
   * @return a collection of all apps
   */
  @Override
  public Collection<App> listApps() {
    return appRepository.getAll();
  }

  /**
   * Installs an application by ID.
   *
   * @param userId the ID of the user
   * @param appId the ID of the app
   * @throws RuntimeException if the app is invalid or already installed
   */
  @Override
  public void installApp(final int userId, final int appId) {
    final App app = appRepository.findById(appId);
    if (app == null) {
      throw new RuntimeException("App not found. ");
    }

    if (installationRepository.isInstalled(userId, appId)) {
      throw new RuntimeException("Already installed.");
    }

    installationRepository.installed(userId, appId);
  }

  /**
   * Uninstalls an application.
   *
   * @param userId the ID of the user
   * @param appId the ID of the app
   * @throws RuntimeException if the app is not currently installed
   */
  @Override
  public void unInstallApp(final int userId, final int appId) {
    final App app = appRepository.findById(appId);

    if (app == null) {
      throw new RuntimeException("App not found. ");
    }

    if (!installationRepository.isInstalled(userId, appId)) {
      throw new RuntimeException("App is not installed in this account.");
    }

    boolean success = installationRepository.unInstalled(userId, appId);

    if (!success) {
      throw new RuntimeException("Uninstall Failed due to server error.");
    }
  }

  /**
   * Submits a user review for an application.
   *
   * @param review the review object containing rating and comment
   * @throws RuntimeException if the app ID is invalid or rating is out of range
   */
  @Override
  public void writeReview(final Review review) {
    if (appRepository.findById(review.getAppId()) == null) {
      throw new RuntimeException("App not found with ID: " + review.getAppId());
    }

    if (review.getRating() < 1 || review.getRating() > 5) {
      throw new RuntimeException("Rating must be between 1 and 5.");
    }

    appRepository.addReview(review);
  }
}
