package org.src.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.src.model.App;
import org.src.model.Review;
import org.src.model.User;
import org.src.repository.AppRepository;
import org.src.repository.InstallationRepository;
import org.src.util.Input;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Implementation of the application management services.
 * <p>
 * This class serves as the central logic layer, implementing multiple interfaces
 * to handle administrative tasks (AppService), user interactions (AppUserService),
 * and reporting data (AppReportService).
 */
@Service
public class AppServiceImpl implements AppService {

    @Autowired
    private AppRepository appRepository;

    @Autowired
    private InstallationRepository installationRepository;

    /**
     * Creates a new application after prompting the user for details.
     * Validates that the App ID does not already exist before saving.
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
     * Checks if the current user matches the author name before allowing updates.
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
     * Requires author verification before deletion.
     */
    @Override
    public void deleteApp(final int appId, final int authorId) {
        final App existingApp = appRepository.findById(appId);
        if (existingApp == null) {
            throw new RuntimeException("App not found.");
        }

        if (existingApp.getAuthor().getUserId() != authorId) {
            throw new RuntimeException("You are not allowed Delete this apps.");
        }

        appRepository.delete(appId);
    }

    /**
     * Lists all available applications in the repository.
     */
    @Override
    public Collection<App> listApps() {
        return appRepository.getAll();
    }

    /**
     * Installs an application by ID.
     * Marks the app as installed and increments its global install count.
     */
    @Override
    public void installApp(final int userId,final int appId) {
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
     * Marks the app as not installed locally.
     */
    @Override
    public void unInstallApp(final int userId,final  int appId) {
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

