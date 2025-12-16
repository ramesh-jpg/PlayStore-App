package app;

import service.AppReportService;
import service.AppService;
import util.Input;

/**
 * Manages the main application menu and user interactions.
 * <p>
 * This class acts as the user interface layer, displaying options and delegating
 * specific tasks to the appropriate service interfaces based on user input.
 */

public final class AppMenu {

    /**
     * Starts the interactive menu loop for the application.
     * <p>
     * Displays available operations such as creating, updating, deleting,
     * and installing apps. The loop continues until the user chooses to logout.
     *
     * @param appService     Service for administrative operations (create, update, delete).
     * @param reportService  Service for generating reports (view installed apps, counts).
     */

    // 3 interface for parameters
    public static void start(final AppService appService,
                             final AppReportService reportService) {

        while (true) {
            System.out.println("\n--- PlayStore Menu ---");

            System.out.println("\n1. Create App\n2. Update App\n3. Delete App\n4. List All Apps\n5. Install App\n6. Uninstall App\n7. Display All Install App\n8. Installation Count For Author\n9. Logout");

            final int choice = Input.readInt("Choice: ");

            switch (choice) {

                //AppService Operation
                case 1 -> appService.createApp();
                case 2 -> appService.updateApp();
                case 3 -> appService.deleteApp();
                case 4 -> appService.listApps();
                case 5 -> appService.installApp();
                case 6 -> appService.unInstallApp();

                //Report Operation
                case 7 -> reportService.showInstalledApps();
                case 8 -> reportService.countInstallByAuthor();

                //Logout From the PlayStore for
                case 9 -> {
                    System.out.println("Logout From PlayStore.");
                    return;
                }
                default -> System.out.println("Invalid");
            }
        }
    }
}

