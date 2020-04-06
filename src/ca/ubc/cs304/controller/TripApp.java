package ca.ubc.cs304.controller;

import ca.ubc.cs304.database.NewDatabaseConnectionHandler;
import ca.ubc.cs304.delegates.AllGroupsDelegate;
import ca.ubc.cs304.delegates.AllTravellersDelegate;
import ca.ubc.cs304.delegates.InterestListWindowDelegate;
import ca.ubc.cs304.delegates.LoginWindowDelegate;
import ca.ubc.cs304.delegates.MainWindowDelegate;
import ca.ubc.cs304.ui.AllGroups;
import ca.ubc.cs304.ui.AllTravellers;
import ca.ubc.cs304.ui.LoginWindow;
import ca.ubc.cs304.ui.MainWindow;
import ca.ubc.cs304.delegates.*;
import ca.ubc.cs304.model.TravellerModel;
import ca.ubc.cs304.model.GroupModel;
import ca.ubc.cs304.model.InterestModel;
import ca.ubc.cs304.ui.*;

import java.sql.SQLException;
import java.util.List;

import java.sql.SQLException;
import java.util.List;

/**
 * This is the main controller class that will orchestrate everything.
 */
public class TripApp implements LoginWindowDelegate, MainWindowDelegate, AllTravellersDelegate, AllGroupsDelegate, InterestListWindowDelegate, AddNewTravellerWindowDelegate, DeleteExistingTravellerWindowDelegate {
    private NewDatabaseConnectionHandler dbHandler;
    private LoginWindow loginWindow = null;
    private MainWindow mainWindow = null;
    private AllTravellers allTravellers = null;
    private AllGroups allGroups = null;
    private InterestListWindow allInterests = null;
    private InterestGroupListWindow interestGroups = null;

    private AddNewTravellerWindow addNewTravellerWindow = null;
    private DeleteExistingTravellerWindow deleteExistingTravellerWindow = null;


    public TripApp() {
        dbHandler = new NewDatabaseConnectionHandler();
    }

    private void start() {
        loginWindow = new LoginWindow();
        loginWindow.showFrame(this);
        // login automatically
        // this.testLogin("ora_yeramko", "a80591878");
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


    public void goToInterestsPage() {
        mainWindow.dispose();
        allInterests = new InterestListWindow();
        allInterests.showFrame(this);
    }

    public void goToInterestGroupsPage(GroupModel[] groupResults) {
        interestGroups = new InterestGroupListWindow(groupResults);
        interestGroups.showFrame(this);
    }

    @Override
    public InterestModel[] getAllInterests() {
        return dbHandler.getAllInterests();
    }

    @Override
    public GroupModel[] findGroupsWithInterest(String interestName) {
        return dbHandler.getGroupsBasedOnInterest(interestName);
    }

    public void goToGroupsPage(){
        mainWindow.dispose();
        allGroups = new AllGroups();
        allGroups.showFrame(this);

    }


    @Override
    public void goToMainWindowFromGroups() {
        allGroups.dispose();
        mainWindow = new MainWindow();
        mainWindow.showFrame(this);
    }

    @Override
    public List<String[]> viewAllGroups() throws SQLException {
        return dbHandler.viewAllGroups();

    }

    @Override
    public List<String[]> getGroupInfo(String groupTitle) throws SQLException {
        return dbHandler.getGroupInfo(groupTitle);
    }

    @Override
    public int countAllMember(String groupID) throws SQLException {
        return dbHandler.countAllMember(groupID);
    }

    @Override
    public List<String[]> viewAllGroupMembers(String groupID){
        return dbHandler.viewAllGroupMembers(groupID);
    }


    @Override
    public List<String[]> viewGroupTrips(String groupID) throws SQLException{
        return dbHandler.viewGroupTrips(groupID);
    }
    @Override
    public List<String[]> viewTripActivity(String groupID, String tripID) throws SQLException{
        return dbHandler.viewTripActivity(groupID, tripID);
    }

    @Override
    public List<String[]> findGroupWithEveryOne() throws SQLException {
        return dbHandler.findGroupWithEveryOne();
    }
    @Override
    public void updateActivityDescrip(String activityID, String descrip) throws SQLException{
        dbHandler.updateActivityDescrip(activityID, descrip);
        allGroups.dispose();
        allGroups = new AllGroups();
        allGroups.showFrame(this);
    }

    @Override
    public double getAverageTripActivities() throws SQLException {
        return dbHandler.getAverageTripActivities();
    }

    public void goAddNewTraveller() {
        allTravellers.dispose();
        addNewTravellerWindow = new AddNewTravellerWindow();
        addNewTravellerWindow.showFrame(this);
    }

    public void goDeleteTraveller() {
        allTravellers.dispose();
        deleteExistingTravellerWindow = new DeleteExistingTravellerWindow();
        deleteExistingTravellerWindow.showFrame(this);
    }

    @Override
    public void goToMainWindow() {
        allTravellers.dispose();
        mainWindow = new MainWindow();
        mainWindow.showFrame(this);
    }



    @Override
    public void goFromInterestsToMainWindow() {
        allInterests.dispose();
        mainWindow = new MainWindow();
        mainWindow.showFrame(this);
    }

    @Override
    public List<String[]> viewAllUsers() throws SQLException {
        return dbHandler.viewAllUsers();
    }

    @Override
    public List<String[]> getTravellerInfoBasedOnTitle(String title) throws SQLException {
        return dbHandler.getTravellerInfoBasedOnTitle(title);
    }


    public void insertNewTraveller(TravellerModel travellerModel) {
        dbHandler.insertTraveller(travellerModel);
        addNewTravellerWindow.dispose();
        allTravellers = new AllTravellers();
        allTravellers.showFrame(this);
    }

    public void deleteTravellerIfExists(String username) {
        dbHandler.deleteUser(username);
        deleteExistingTravellerWindow.dispose();
        allTravellers = new AllTravellers();
        allTravellers.showFrame(this);
    }

}
