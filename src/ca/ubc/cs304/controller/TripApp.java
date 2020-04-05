package ca.ubc.cs304.controller;

import ca.ubc.cs304.database.NewDatabaseConnectionHandler;
import ca.ubc.cs304.delegates.AllGroupsDelegate;
import ca.ubc.cs304.delegates.AllTravellersDelegate;
import ca.ubc.cs304.delegates.LoginWindowDelegate;
import ca.ubc.cs304.delegates.MainWindowDelegate;
import ca.ubc.cs304.ui.AllGroups;
import ca.ubc.cs304.ui.AllTravellers;
import ca.ubc.cs304.ui.LoginWindow;
import ca.ubc.cs304.ui.MainWindow;

import java.sql.SQLException;
import java.util.List;

/**
 * This is the main controller class that will orchestrate everything.
 */
public class TripApp implements LoginWindowDelegate, MainWindowDelegate, AllTravellersDelegate, AllGroupsDelegate {
    private NewDatabaseConnectionHandler dbHandler = null;
    private LoginWindow loginWindow = null;
    private MainWindow mainWindow = null;
    private AllTravellers allTravellers = null;
    private AllGroups allGroups = null;


    public TripApp() {
        dbHandler = new NewDatabaseConnectionHandler();
    }

    private void start() {
        loginWindow = new LoginWindow();
        loginWindow.showFrame(this);
        //login automatically
        this.testLogin("ora_yeramko", "a80591878");
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


    /**
     * Main method called at launch time
     */
    public static void main(String args[]) {
        TripApp tripApp = new TripApp();
        tripApp.start();
    }



    public void goToTravellersPage() {
        mainWindow.dispose();
        allTravellers = new AllTravellers();
        allTravellers.showFrame(this);

    }

    public void goToGroupsPage(){
        mainWindow.dispose();
        allGroups = new AllGroups();
        allGroups.showFrame(this);

    }

    @Override
    public List<String[]> viewAllGroups() throws SQLException {
        return dbHandler.viewAllGroups();

    }

    @Override
    public List<String[]> getGroupInfo(int Group_ID) throws SQLException {
        return dbHandler.getGroupInfo();
    }
}
