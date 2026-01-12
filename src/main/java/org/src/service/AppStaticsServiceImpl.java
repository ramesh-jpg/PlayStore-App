package org.src.service;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.src.model.App;
import org.src.repository.AppRepository;
import org.src.repository.InstallationRepository;

/**
 * Implementation of the AppStaticsService.
 *
 * <p>This class fetches raw data from repositories and performs filtering and aggregation to
 * generate user reports and statistics.
 */
@Service
public class AppStaticsServiceImpl implements AppStaticsService {

  private final AppRepository appRepository;
  private final InstallationRepository installationRepository;

  /**
   * Constructs the service with required repository dependencies.
   *
   * <p>Constructor injection ensures that the service is initialized with valid repositories
   *
   * @param appRepository the repository for App data
   * @param installationRepository the repository for Installation data
   */
  @Autowired
  public AppStaticsServiceImpl(final AppRepository appRepository,
                               final InstallationRepository installationRepository) {
    this.appRepository = appRepository;
    this.installationRepository = installationRepository;
  }

  /**
   * Retrieves all apps currently installed by a specific user.
   *
   * @param userId the unique identifier of the user
   * @return a collection of installed {@link App} objects
   */
  @Override
  public Collection<App> showInstalledApps(final int userId) {
    return installationRepository.getInstalledApps(userId);
  }

  /**
   * Calculates the total number of installations for a specific author.
   *
   * <p>This method iterates through all apps, matches the author name, and aggregates the installed
   * counts.
   *
   * @param authorName the name of the author to calculate stats for
   * @return the total installation count across all apps owned by the author
   */
  @Override
  public int countInstallByAuthor(final String authorName) {
    int totalCount = 0;
    String searchName = authorName.trim().toLowerCase();
    for (final App app : appRepository.getAll()) {
      if (app.getAuthorName().trim().equalsIgnoreCase(searchName)) {
        totalCount += app.getInstalledCount();
      }
    }
    return totalCount;
  }
}
