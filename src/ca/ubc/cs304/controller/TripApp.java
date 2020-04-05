package ca.ubc.cs304.controller;

import ca.ubc.cs304.database.NewDatabaseConnectionHandler;
import ca.ubc.cs304.delegates.AddNewTravellerWindowDelegate;
import ca.ubc.cs304.delegates.AllTravellersDelegate;
import ca.ubc.cs304.delegates.LoginWindowDelegate;
import ca.ubc.cs304.delegates.MainWindowDelegate;
import ca.ubc.cs304.ui.AddNewTravellerWindow;
import ca.ubc.cs304.ui.AllTravellers;
import ca.ubc.cs304.ui.LoginWindow;
import ca.ubc.cs304.ui.MainWindow;

/**
 * This is the main controller class that will orchestrate everything.
 */
public class TripApp implements LoginWindowDelegate, MainWindowDelegate, AllTravellersDelegate, AddNewTravellerWindowDelegate {
    private NewDatabaseConnectionHandler dbHandler = null;
    private LoginWindow loginWindow = null;
    private MainWindow mainWindow = null;
    private AllTravellers allTravellers = null;
    private AddNewTravellerWindow addNewTravellerWindow = null;


    public TripApp() {
        dbHandler = new NewDatabaseConnectionHandler();
    }

    private void start() {
        loginWindow = new LoginWindow();
        loginWindow.showFrame(this);
        //login automatically
//        this.testLogin("ora_yeramko", "a80591878");
    }

    /**
     * LoginWindowDelegate Implementation
     *
     * connects to Oracle database with supplied username and password
     */
    public void login(String username, String password) {
        boolean didConnect = dbHandler.login(username, password);

        if (didConnect) {
            // Once connected, remove login window and start text transaction flow
            loginWindow.dispose();
            mainWindow = new MainWindow();
            mainWindow.showFrame( this);

        } else {
            loginWindow.handleLoginFailed();

            if (loginWindow.hasReachedMaxLoginAttempts()) {
                loginWindow.dispose();
                System.out.println("You have exceeded your number of allowed attempts");
                System.exit(-1);
            }
        }
    }

    public void testLogin(String username, String password) {
        boolean didConnect = dbHandler.login(username, password);

        if (didConnect) {
            mainWindow = new MainWindow();
            mainWindow.showFrame(this);
        } else {
            System.out.println("Fail to connect to DB");
        }
    }

    /**
     * TerminalTransactionsDelegate Implementation
     *
     * The TerminalTransaction instance tells us that it is done with what it's
     * doing so we are cleaning up the connection since it's no longer needed.
     */
    public void terminalTransactionsFinished() {
        dbHandler.close();
        dbHandler = null;

        System.exit(0);
    }


    /**
     * TerminalTransactionsDelegate Implementation
     *
     * The TerminalTransaction instance tells us that the user is fine with dropping any existing table
     * called branch and creating a new one for this project to use
     */
    public void databaseSetup() {
        dbHandler.databaseSetup();;

    }

    /**
     * Main method called at launch time
     */
    public static void main(String args[]) {
        TripApp tripApp = new TripApp();
        tripApp.start();
    }


    /* Method goes to Page with All Travellers */
    public void goToTravellersPage() {
        mainWindow.dispose();
        allTravellers = new AllTravellers();
        allTravellers.showFrame(this);

    }

    public void goAddNewTraveller() {
        allTravellers.dispose();
        addNewTravellerWindow = new AddNewTravellerWindow();
        addNewTravellerWindow.showFrame(this);
    }

    public void goDeleteTraveller() {

    }
}
