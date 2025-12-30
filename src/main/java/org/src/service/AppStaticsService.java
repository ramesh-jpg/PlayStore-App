package org.src.service;

import java.util.Collection;
import org.src.model.App;

/**
 * Defines operations for generating reports and viewing application statistics.
 *
 * <p>This interface focuses on data retrieval and analysis, such as listing installed applications
 * and calculating installation metrics.
 */
public interface AppStaticsService {

  /**
   * Retrieves a list of all applications currently installed by a specific user.
   *
   * @param userId the unique identifier of the user
   * @return a collection of {@link App} objects installed by the user
   */
  Collection<App> showInstalledApps(int userId);

  /**
   * Calculates the total number of installations for a specific author.
   *
   * @param authorName the name of the author to calculate stats for
   * @return the total count of installations across all apps owned by the author
   */
  int countInstallByAuthor(String authorName);
}
