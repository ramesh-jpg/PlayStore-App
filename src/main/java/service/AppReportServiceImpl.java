package service;

import model.App;
import repository.AppRepository;
import util.Input;

import java.util.Collection;
import java.util.stream.Collectors;

public class AppReportServiceImpl implements AppReportService {

    private final AppRepository appRepository;

    public AppReportServiceImpl(final AppRepository repository) {
        this.appRepository = repository;
    }

    @Override
    public void showInstalledApps() {
        final Collection<App> allApps = appRepository.getAll();

        final Collection<App> installedAppList = allApps.stream()
                .filter(App::isInstalled)
                .collect(Collectors.toList());

        System.out.println("\nInstalled Apps List. ");
        if (installedAppList.isEmpty()) {
            System.out.println("No apps installed.");
        } else {
            installedAppList.forEach(app -> System.out.println("-> " + app.getName()));
        }

    }

    /**
     * Calculates and displays the total number of installations for a specific author.
     * This method iterates through all apps, matches the author name ,
     * and aggregates the installed counts.
     */
    @Override
    public void countInstallByAuthor() {
        final String author = Input.readString("Author Name: ").trim().toLowerCase();

        int totalCount = 0;

        for (final App app : appRepository.getAll()) {

            if (app.getAuthorName().trim().toLowerCase().equals(author)) {
                System.out.println(app.getName() + " - " + app.getInstallCount());
                totalCount += app.getInstallCount();
            }
        }

        System.out.println("Total Installs for " + author + " = " + totalCount);
    }
}
