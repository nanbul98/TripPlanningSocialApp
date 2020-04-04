package ca.ubc.cs304.database;

import ca.ubc.cs304.model.BusinessModel;
import ca.ubc.cs304.model.GroupModel;
import ca.ubc.cs304.model.TravellerModel;
import ca.ubc.cs304.model.UserModel;

import java.sql.*;
import java.util.ArrayList;

public class NewDatabaseConnectionHandler {
    // Use this version of the ORACLE_URL if you are running the code off of the server
//	private static final String ORACLE_URL = "jdbc:oracle:thin:@dbhost.students.cs.ubc.ca:1522:stu";
    // Use this version of the ORACLE_URL if you are tunneling into the undergrad servers
    private static final String ORACLE_URL = "jdbc:oracle:thin:@localhost:1522:stu";
    private static final String EXCEPTION_TAG = "[EXCEPTION]";
    private static final String WARNING_TAG = "[WARNING]";

    private Connection connection = null;

    public NewDatabaseConnectionHandler() {
        try {
            // Load the Oracle JDBC driver
            // Note that the path could change for new drivers
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    private void rollbackConnection() {
        try  {
            connection.rollback();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    public void deleteUser(UserModel user) {
        try {
            if (user instanceof TravellerModel) {
                PreparedStatement ps = connection.prepareStatement("DELETE FROM traveller WHERE username = ?");
                String username = user.getUsername();
                ps.setString(1, username);

                int rowCount = ps.executeUpdate();
                if (rowCount == 0) {
                    System.out.println(WARNING_TAG + " Traveller " + username + " does not exist!");
                }

                connection.commit();

                ps.close();
            } else {
                PreparedStatement ps = connection.prepareStatement("DELETE FROM business WHERE username = ?");
                String username = user.getUsername();
                ps.setString(1, username);

                int rowCount = ps.executeUpdate();
                if (rowCount == 0) {
                    System.out.println(WARNING_TAG + " Business " + username + " does not exist!");
                }

                connection.commit();

                ps.close();


            }
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public BusinessModel[] getBusinessInfoBasedOnTitle(String title) {
        ArrayList<BusinessModel> result = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM business WHERE name = ?");
            ps.setString(1,title);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                BusinessModel model = new BusinessModel(rs.getString("username"),
                        rs.getString("name"),
                        rs.getString("country"),
                        rs.getString("province"),
                        rs.getString("city"));
                result.add(model);
            }

            rs.close();
            ps.close();

        } catch(SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new BusinessModel[result.size()]);
    }

    public GroupModel[] getGroupsBasedOnInterest(String keyword) {
        ArrayList<GroupModel> result = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT tg.Title, tg.Description, tg.Group_ID, tg.Owner_Username FROM traveller_group tg, trav_group_interests tgi WHERE tgi.Group_ID = tg.Group_ID AND tgi.Interest_Name LIKE ?" +
                    "UNION" + "SELECT bg.Title, bg.Description, bg.Group_ID, bg.Owner_Username FROM business_group tg, bus_group_interests bgi WHERE bgi.Group_ID = bg.Group_ID AND bgi.Interest_Name LIKE ?");
            ps.setString(1,keyword);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                GroupModel model = new GroupModel(rs.getInt("groupID"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("owner_username"));
                result.add(model);
            }

            rs.close();
            ps.close();

        } catch(SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new GroupModel[result.size()]);
    }







}
