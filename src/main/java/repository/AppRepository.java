package repository;

import model.App;
import model.Review;

import java.util.Collection;

/**
 * Repository interface for managing App entities.
 * This defines standard CRUD-style operations used in the service layer.
 */

public interface AppRepository {

    /**
     * Persists a new application to the storage.
     *
     * @param app The {@link App} object containing details to be saved.
     */
    void save(App app);

    /**
     * Retrieves a specific application by its unique identifier.
     *
     * @param id The unique ID of the application.
     * @return The {@link App} object if found, or {@code null} if no match exists.
     */
    App findById(int id);

    /**
     * Updates the details of an existing application.
     *
     * @param app The {@link App} object containing updated information.
     */
    void update(App app);

    /**
     * Removes an application from the storage permanently.
     *
     * @param id The unique ID of the application to delete.
     * @return {@code true} if the deletion was successful, {@code false} otherwise.
     */
    boolean delete(int id);

    /**
     * Retrieves all applications currently stored in the system.
     *
     * @return A collection of all {@link App} objects.
     */
    Collection<App> getAll();

    void addReview(Review review);
}

