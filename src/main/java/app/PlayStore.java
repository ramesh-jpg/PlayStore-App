package app;

import model.User;
import service.AppReportServiceImpl;
import service.AppServiceImpl;
import repository.AppRepositoryImpl;
import service.UserService;
import service.UserServiceImpl;
import util.Input;

/**
 * Bootstraps the PlayStore application and manages the user authentication loop.
 * <p>
 * This class initializes the core services and handles the initial sign-up and
 * login flow before control to the application menu.
 */

public final class PlayStore {

    /**
     * The main entry point of the application.
     * <p>
     * Initializes the service layers and keeps the application running in a loop
     * to allow users to sign up, sign in, or exit.
     */

    public static void main(String[] args) {

        final UserService userService = new UserServiceImpl();

        final AppServiceImpl appService = new AppServiceImpl(new AppRepositoryImpl());

        final AppReportServiceImpl reportService = new AppReportServiceImpl(new AppRepositoryImpl());

        while (true) {
            System.out.println("\n1. Sign Up\n2. Login\n3. Exit");
            final int choice = Input.readInt("Choice: ");

            switch (choice) {
                case 1:
                    userService.signUp();
                    break;
                case 2:
                    final User signInUser = userService.signIn();
                    if (signInUser != null) {
                        AppMenu.start(appService, reportService, signInUser);
                    }
                    break;
                case 3:
                    System.out.println("Exiting to PlayStore.");
                    return;
                default:
                    System.out.println("Invalid.");
            }
        }
    }
}


