package org.src.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.src.model.App;
import org.src.model.User;
import org.src.repository.AppRepository;
import org.src.repository.InstallationRepository;
import org.src.util.Input;
import java.util.Collection;


/**
 * Implementation of the Report Service.
 * <p>
 * This class fetches raw data from {@link AppRepository} and performs
 * filtering and sorting to generate user reports.
 */
@Service
public class AppReportServiceImpl implements AppReportService {

    @Autowired
    private AppRepository appRepository;

    @Autowired
    private InstallationRepository installationRepository;
    /**
     * Fetches all apps and filters only the ones where installed = true.
     */
    @Override
    public Collection<App> showInstalledApps(final int userId) {
        return installationRepository.getInstalledApps(userId);
    }

    /**
     * Calculates and displays the total number of installations for a specific author.
     * This method iterates through all apps, matches the author name ,
     * and aggregates the installed counts.
     */
    @Override
    public int countInstallByAuthor(final String authorName) {
        int totalCount = 0;
        String searchName = authorName.trim().toLowerCase();
        for (final App app : appRepository.getAll()) {
            if (app.getAuthorName().trim().toLowerCase().equals(searchName)) {
                totalCount += app.getInstalledCount();
            }
        }
        return totalCount;
    }
}
