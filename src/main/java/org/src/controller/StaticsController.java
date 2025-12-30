package org.src.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.src.model.App;
import org.src.service.AppStaticsService;

/**
 * REST Controller for retrieving application statistics and analytics.
 *
 * <p>This controller provides endpoints to generate reports, such as the list of apps installed by
 * a user and the total installation counts for authors.
 */
@RestController
@RequestMapping("/api/statics")
public class StaticsController {

  private final AppStaticsService staticsService;

  /**
   * Constructs the StaticsController with the required service dependency.
   *
   * <p>Constructor injection is used to ensure the controller
   *
   * @param staticsService the service used to fetch application statics
   */
  @Autowired
  public StaticsController(final AppStaticsService staticsService) {
    this.staticsService = staticsService;
  }

  /**
   * Retrieves the list of applications installed by a specific user.
   *
   * @param userId the unique identifier of the user
   * @return a {@link ResponseEntity} containing the collection of installed apps
   */
  @GetMapping("/installed/{userId}")
  public ResponseEntity<Collection<App>> showInstalledApps(@PathVariable final int userId) {
    final Collection<App> apps = staticsService.showInstalledApps(userId);

    if (apps.isEmpty()) {
      return ResponseEntity.noContent().build();
    }

    return ResponseEntity.ok(apps);
  }

  /**
   * Calculates the total number of installations for a specific author apps.
   *
   * <p>This endpoint aggregates installation counts across all apps belong to the given author
   * name.
   *
   * @param authorName the name of the author to calculate stats for
   * @return the {@link ResponseEntity} containing a map with the author's name and their total
   *     installation count
   */
  @GetMapping("/author")
  public ResponseEntity<Map<String, Object>> countInstallByAuthor(
      @RequestParam final String authorName) {
    final int count = staticsService.countInstallByAuthor(authorName);
    final Map<String, Object> response = new HashMap<>();
    response.put("author", authorName);
    response.put("totalInstalls", count);

    return ResponseEntity.ok(response);
  }
}
