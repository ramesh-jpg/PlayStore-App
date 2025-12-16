package service;

/**
 * Defines operations for generating reports and viewing application statistics.
 * <p>
 * This interface focuses on data retrieval and analysis, such as listing
 * installed applications and calculating installation metrics.
 */
public interface AppReportService {

    /**
     * Displays a list of all applications currently installed on the device.
     */
    void showInstalledApps();

    /**
     * Calculates and displays the total number of installations for a specific author.
     */
    void countInstallByAuthor();

}
