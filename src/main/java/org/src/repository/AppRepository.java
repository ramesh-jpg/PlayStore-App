package org.src.repository;

import java.util.Collection;
import org.src.model.App;
import org.src.model.Review;

/**
 * Repository interface for managing App entities.
 *
 * <p>This interface defines standard CRUD-style operations (Create, Read, Update, Delete) that are
 * used by the service layer to interact with the data storage.
 */
public interface AppRepository {

  /**
   * Persists a new application to the storage.
   *
   * @param app the {@link App} object containing details to be saved
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
   * @return a collection of all {@link App} objects available in the store
   */
  Collection<App> getAll();

  /**
   * Adds a user review to a specific application.
   *
   * <p>This method links a {@link Review} to an {@link App} .
   *
   * @param review the review object containing the user's rating and comment
   */
  void addReview(Review review);
}
