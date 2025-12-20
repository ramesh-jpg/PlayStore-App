package service;

import model.App;
import model.Author;
import model.Review;
import model.User;
import repository.AppRepository;
import util.Input;

import java.util.Arrays;
import java.util.List;

/**
 * Implementation of the application management services.
 * <p>
 * This class serves as the central logic layer, implementing multiple interfaces
 * to handle administrative tasks (AppService), user interactions (AppUserService),
 * and reporting data (AppReportService).
 */

public class AppServiceImpl implements AppService {

    private final AppRepository appRepository;

   /**
    *Constructs the service with a specific repository.
    *
    * @param repository The data storage mechanism for App objects.
    */

    public AppServiceImpl(final AppRepository repository) {
        this.appRepository = repository;
    }

    /**
     * Creates a new application after prompting the user for details.
     * Validates that the App ID does not already exist before saving.
     */
    @Override
    public void createApp() {

        final String name = Input.readString("App Name: ");
        final String authorName = Input.readString("AuthorName: ");
        Author author = new Author(0,authorName);
        final String description = Input.readString("Description: ");
        final double version = Input.readDouble("Version: ");
        final String featuresInput = Input.readString("Features: ");
        final List<String> features = Arrays.asList(featuresInput.split(","));

        appRepository.save(new App( 0,name, author, description, version, features,0.0,0));
        System.out.println("App Created.");
    }

    /**
     * Updates an existing application's details.
     * Checks if the current user matches the author name before allowing updates.
     */
    @Override
    public void updateApp() {
        final int appId = Input.readInt("App Id: ");
        final App existingApp = appRepository.findById(appId);

        if (existingApp == null) {
            System.out.println("App not found. ");
            return;
        }
        final String currentAuthor = Input.readString("AuthorName: ");

        if (!existingApp.getAuthorName().equalsIgnoreCase(currentAuthor)) {
            System.out.println("You are not allowed to update this app.");
            return;
        }

        final String name = Input.readString("New Name: ");
        final String description = Input.readString("Description: ");
        final double version = Input.readDouble("New Version: ");
        final String featuresInput = Input.readString("New Features: ");
        final List<String> features = Arrays.asList(featuresInput.split(","));
        existingApp.setName(name);
        existingApp.setFeatures(features);
        existingApp.setDescription(description);
        existingApp.setVersion(version);


        appRepository.update(existingApp);
        System.out.println("App Updated. ");
    }

    /**
     * Deletes an application from the store.
     * Requires author verification before deletion.
     */
    @Override
    public void deleteApp() {
        final int appId = Input.readInt("App Id: ");
        final App existingApp = appRepository.findById(appId);

        if (existingApp == null) {
            System.out.println("App not found.");
            return;
        }

        final String currentAuthor = Input.readString("AuthorName: ");

        if (!existingApp.getAuthorName().equalsIgnoreCase(currentAuthor)) {
            System.out.println("You are not allowed delete this app.");
            return;
        }

        if (appRepository.delete(appId)) {
            System.out.println("App deleted from playStore. ");
        }

    }

    /**
     * Lists all available applications in the repository.
     */
    @Override
    public void listApps() {
        appRepository.getAll().forEach(System.out::println);
    }

    /**
     * Installs an application by ID.
     * Marks the app as installed and increments its global install count.
     */
    @Override
    public void installApp() {
        final int appId = Input.readInt("App Id to Install: ");
        final App app = appRepository.findById(appId);

        if (app == null) {
            System.out.println("App not found. ");
            return;
        }

        if (app.isInstalled()) {
            System.out.println(app.getName() + " is already installed.");
            return;
        }

        app.install();
        app.installedCount();
        System.out.println(app.getName() + " Installed. ");
    }

    /**
     * Uninstalls an application.
     * Marks the app as not installed locally.
     */
    public void unInstallApp() {
        final int appId = Input.readInt("App Id to Uninstall: ");
        final App app = appRepository.findById(appId);

        if (app == null) {
            System.out.println("App not found. ");
            return;
        }

        if (!app.isInstalled()) {
            System.out.println("App not installed.");
            return;
        }

        app.uninstall();
        System.out.println(app.getName() + " UnInstalled.");
    }

    public void writeReview(final User signInUser) {
        System.out.println("Write a Review.");

        final int appId = Input.readInt("Enter App ID to review: ");

        if (appRepository.findById(appId) == null) {
            System.out.println("App not found!");
            return;
        }

        final double rating = Input.readDouble("Rating : ");
        final String comment = Input.readString("Comment: ");

        final Review review = new Review(0, signInUser.getUserId(), signInUser.getUsername(), appId, rating, comment);
        appRepository.addReview(review);
    }

}
