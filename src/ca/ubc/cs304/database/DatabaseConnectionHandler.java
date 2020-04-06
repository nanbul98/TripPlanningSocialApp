package ca.ubc.cs304.database;


import ca.ubc.cs304.model.GroupModel;
import ca.ubc.cs304.model.InterestModel;
import ca.ubc.cs304.model.TravellerModel;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//import ca.ubc.cs304.model.UserModel;

import static javax.swing.JOptionPane.showMessageDialog;

public class DatabaseConnectionHandler {
    // Use this version of the ORACLE_URL if you are running the code off of the server
//	private static final String ORACLE_URL = "jdbc:oracle:thin:@dbhost.students.cs.ubc.ca:1522:stu";
    // Use this version of the ORACLE_URL if you are tunneling into the undergrad servers
    private static final String ORACLE_URL = "jdbc:oracle:thin:@localhost:1522:stu";
    private static final String EXCEPTION_TAG = "[EXCEPTION]";
    private static final String WARNING_TAG = "[WARNING]";

    private Connection connection = null;

    public DatabaseConnectionHandler() {
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

    public boolean login(String username, String password) {
        try {
            if (connection != null) {
                connection.close();
            }

            connection = DriverManager.getConnection(ORACLE_URL, username, password);
            connection.setAutoCommit(false);

            System.out.println("\nConnected to Oracle!");
            return true;
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            return false;
        }
    }

    private void rollbackConnection() {
        try  {
            connection.rollback();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    //queries start
    //queries related to Traveller

    public void insertTraveller(TravellerModel model) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO traveller VALUES (?,?,?,?,?,?,?)");
            ps.setString(1, model.getUsername());
            ps.setString(2, model.getName());
            ps.setString(3, model.getCountry());
            ps.setString(4, model.getProvince());
            ps.setString(5, model.getCity());
            ps.setString(6, model.getGender());
            ps.setString(7, model.getDOB());

            ps.executeUpdate();
            connection.commit();

            ps.close();

        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }

    }


    public void deleteUser(String user) {
        try {
                String sql = "DELETE FROM traveller \n" + "WHERE Username = ?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, user);

                int rowCount = ps.executeUpdate();
                if (rowCount == 0) {
                    System.out.println(WARNING_TAG + " Traveller " + user + " does not exist!");
                }

                connection.commit();

                ps.close();

        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    //Viewing all Users table
    public List<String[]> viewAllUsers() throws SQLException{
        List<String[]> res = new ArrayList<>();
        String[] colName = {"Username","Name","Country","Province","City","Gender","Birthdate"};
        res.add(colName);

        try {
            String sql = "SELECT * FROM traveller";
            PreparedStatement prepState;
            prepState = connection.prepareStatement(sql);
            //PreparedStatement stmt = connection.createStatement();
            //ResultSet rs = stmt.executeQuery("SELECT * FROM traveller_group");

            ResultSet rs = prepState.executeQuery();
            while (rs.next()) {
                String[] row = new String[colName.length];
                row[0] = rs.getString("Username");
                row[1] = rs.getString("Name");
                row[2] = rs.getString("Country");
                row[3] = rs.getString("Province");
                row[4] = rs.getString("City");
                row[5] = rs.getString("Gender");
                row[6] = rs.getString("Birthdate");
                res.add(row);
            }
        } catch (SQLException e) {
            rollbackConnection();
            throw e;
        }
        return res;
    }
    // Search User by name
    public List<String[]> getTravellerInfoBasedOnTitle(String title) {
        List<String[]> res = new ArrayList<>();
        String[] colName = {"Username","Name","Country","Province","City","Gender","Birthdate"};
        res.add(colName);

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM traveller WHERE name = ?");
            ps.setString(1,title);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                String[] row = new String[colName.length];
                row[0] = rs.getString("Username");
                row[1] = rs.getString("Name");
                row[2] = rs.getString("Country");
                row[3] = rs.getString("Province");
                row[4] = rs.getString("City");
                row[5] = rs.getString("Gender");
                row[6] = rs.getString("Birthdate");
                res.add(row);
            }

            rs.close();
            ps.close();

        } catch(SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return res;
    }


    // TODO queries related to Group

    //Viewing all Groups table
    public List<String[]> viewAllGroups() throws SQLException{
        List<String[]> res = new ArrayList<>();
        String[] colName = {"Group_ID","Title"};
        res.add(colName);

        try {
            Statement prepState = connection.createStatement();
            ResultSet rs = prepState.executeQuery("SELECT Group_ID,Title FROM traveller_group");

            while (rs.next()) {
                String[] row = new String[colName.length];
                row[0] = rs.getString("Group_ID");
                row[1] = rs.getString("Title");
                res.add(row);
            }
        } catch (SQLException e) {
            rollbackConnection();
            throw e;
        }
        return res;
    }

    //Viewing specific group of choice by groupTitle
    public List<String[]> getGroupInfo(String groupTitle) throws SQLException{
        List<String[]> res = new ArrayList<>();
        String[] colName = {"Group_ID","Title","Description","Owner_Username"};
        res.add(colName);


        try {
            Statement prepState = connection.createStatement();

            String sql = "SELECT * FROM traveller_group WHERE Title = '" + groupTitle + "\'";
            /*
            if (!groupTitle.equals("")) {
                sql += " WHERE Title = '" + groupTitle + "\'";
            }
             */
            prepState = connection.prepareStatement(sql);

            ResultSet rs = prepState.executeQuery(sql);

            while (rs.next()) {
                String[] row = new String[colName.length];
                row[0] = rs.getString("Group_ID");
                row[1] = rs.getString("Title");
                row[2] = rs.getString("Description");
                row[3] = rs.getString("Owner_Username");
                res.add(row);
            }
        } catch (SQLException e) {
            rollbackConnection();
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            throw e;
        }
        return res;
    }

    // find the total number of people in a group
    public int countAllMember (String groupID) throws SQLException {
        int intGroupID = Integer.parseInt(groupID);
        int count = 0;

        try {
            String sql = "SELECT COUNT(*) AS total \n" +
                    "FROM traveller T, trav_group_member_travellers G\n" +
                    "WHERE T.username = G.username\n" +
                    "GROUP BY G.group_id\n" +
                    "HAVING G.group_id = '" + intGroupID + "\'";

            PreparedStatement prepState;
            prepState = connection.prepareStatement(sql);

            ResultSet rs = prepState.executeQuery();
            while (rs.next()) {
                count = rs.getInt("total");
            }


        } catch (Exception e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return count;

    }

    public InterestModel[] getAllInterests() {
        ArrayList<InterestModel> result = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM interest");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                InterestModel model = new InterestModel(rs.getString("name"),
                        rs.getString("description"));
                result.add(model);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new InterestModel[result.size()]);
    }

    public GroupModel[] getGroupsBasedOnInterest(String keyword) {
        ArrayList<GroupModel> result = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT tg.Title, tg.Description, tg.Group_ID, tg.Owner_Username FROM traveller_group tg, trav_group_interests tgi WHERE tgi.Group_ID = tg.Group_ID AND tgi.Interest_Name = ?");
            ps.setString(1, keyword);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                GroupModel model = new GroupModel(rs.getInt("group_ID"),
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

    // view all the members in a group
    public List<String[]> viewAllGroupMembers (String groupID) {
        int intGroupID = Integer.parseInt(groupID);
        List<String[]> mem = new ArrayList<>();
        String[] colName = {"Username","Name","Country","City","Gender","Birthdate"};
        mem.add(colName);

        System.out.println("view members in" + intGroupID);

        try {
            String sql = "SELECT DISTINCT T.Username, T.Name, T.Country, T.City, T.Gender, T.Birthdate\n" +
                    "FROM traveller T, trav_group_member_travellers G\n" +
                    "WHERE T.Username = G.Username AND G.group_id = '" + intGroupID + "\'";

            PreparedStatement prepState;
            prepState = connection.prepareStatement(sql);
            ResultSet rs = prepState.executeQuery();
            while (rs.next()) {
                String[] row = new String[colName.length];
                row[0] = rs.getString("Username");
                row[1] = rs.getString("Name");
                row[2] = rs.getString("Country");
                row[3] = rs.getString("City");
                row[4] = rs.getString("Gender");
                row[5] = rs.getString("Birthdate");
                mem.add(row);
            }

        } catch (Exception e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return mem;
    }


    // view groups that have All the members
    public List<String[]> viewGroupWithAllMembers () {
        List<String[]> groups = new ArrayList<>();
        String[] colName = {"Group_ID","Title"};
        groups.add(colName);


        try {
            String sql = "SELECT tg.Group_ID, tg.Title\n" +
                    "FROM traveller_group tg\n" +
                    "WHERE NOT EXISTS\n" +
                    "((Select t.Username from traveller t)\n" +
                    "MINUS\n" +
                    "(SELECT tgm.Username\n" +
                    "FROM trav_group_member_travellers tgm\n" +
                    "WHERE tgm.Group_ID = tg.Group_ID));";

            PreparedStatement prepState;
            prepState = connection.prepareStatement(sql);
            ResultSet rs = prepState.executeQuery();
            while (rs.next()) {
                String[] row = new String[colName.length];
                row[0] = rs.getString("Group_ID");
                row[1] = rs.getString("Title");
                groups.add(row);
            }

        } catch (Exception e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return groups;
    }


    // view all trips of a group
    public List<String[]> viewGroupTrips (String groupID) {
        int intGroupID = Integer.parseInt(groupID);
        List<String[]> mem = new ArrayList<>();
        String[] colName = {"Trip_ID","Meeting_Location","Title","Group_Limit","Trip_Cost","Currency","Description", "Start_Date","End_Date"};
        mem.add(colName);

        System.out.println("view trips in group " + intGroupID);

        try {
            String sql = "SELECT Trip_ID, Meeting_Location, Title, Group_Limit, Trip_Cost, Currency, Description, Start_Date, End_Date\n" +
                    "FROM trav_grp_trip\n" +
                    "WHERE Group_ID = '" + intGroupID + "\'";

            PreparedStatement prepState;
            prepState = connection.prepareStatement(sql);
            ResultSet rs = prepState.executeQuery();
            while (rs.next()) {
                String[] row = new String[colName.length];
                row[0] = rs.getString("Trip_ID");
                row[1] = rs.getString("Meeting_Location");
                row[2] = rs.getString("Title");
                row[3] = rs.getString("Group_Limit");
                row[4] = rs.getString("Trip_Cost");
                row[5] = rs.getString("Currency");
                row[6] = rs.getString("Description");
                row[7] = rs.getString("Start_Date");
                row[8] = rs.getString("End_Date");
                mem.add(row);
            }

        } catch (Exception e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return mem;
    }

    // view all activities of group
    public List<String[]> viewTripActivity (String groupID, String tripID) {
        int intGroupID = Integer.parseInt(groupID);
        int intTripID = Integer.parseInt(tripID);

        List<String[]> mem = new ArrayList<>();
        String[] colName = {"Activity_ID","Meeting_Location","Title","Activity_Cost","Description", "Start_Date","End_Date"};
        mem.add(colName);

        System.out.println("view trips in group " + intGroupID);

        try {
            String sql = "SELECT A.Activity_ID, A.Meeting_Location, A.Title, A.Activity_Cost, A.Description, A.Start_Date, A.End_Date\n" +
                    "FROM trav_grp_trip T, trav_grp_trp_activity A\n" +
                    "WHERE T.Group_ID = '" + intGroupID + "\' AND A.Trip_ID = '" + intTripID + "\'";

            PreparedStatement prepState;
            prepState = connection.prepareStatement(sql);
            ResultSet rs = prepState.executeQuery();
            while (rs.next()) {
                String[] row = new String[colName.length];
                row[0] = rs.getString("Activity_ID");
                row[1] = rs.getString("Meeting_Location");
                row[2] = rs.getString("Title");
                row[3] = rs.getString("Activity_Cost");
                row[4] = rs.getString("Description");
                row[5] = rs.getString("Start_Date");
                row[6] = rs.getString("End_Date");
                mem.add(row);
            }

        } catch (Exception e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return mem;
    }

    // find groups that all the travellers are part of
    public List<String[]> findGroupWithEveryOne() throws SQLException{
        List<String[]> res = new ArrayList<>();
        String[] colName = {"GROUP_ID", "title","Description"};
        res.add(colName);

        try {
            Statement s  = connection.createStatement();
            ResultSet rs = s.executeQuery("SELECT Tg.Group_ID, Tg.Title, Tg.Description " +
                    "FROM traveller_group tg " +
                    "WHERE NOT EXISTS " +
                    "((SELECT T.Username FROM traveller T) " +
                    "MINUS " +
                    "(SELECT Tgm.Username " +
                    "FROM trav_group_member_travellers Tgm " +
                    "WHERE Tgm.Group_ID = Tg.Group_ID))");

            while (rs.next()) {
                String[] row = new String[colName.length];
                row[0] = rs.getString("GROUP_ID");
                row[1] = rs.getString("title");
                row[2] = rs.getString("Description");
                res.add(row);
            }
        } catch (SQLException e) {
            rollbackConnection();
            throw e;
        }
        return res;
    }


    public List<String[]> searchGroupMember (int groupID, String name) {
        List<String[]> mem = new ArrayList<>();
        String[] colName = {"Username", "Name", "Location", "Gender", "Birthdate"};
        mem.add(colName);
        try {
            String sql = "SELECT DISTINCT Username, Name, City, Gender, Birthdate\n" +
                    "FROM traveller T, group_member_travellers G\n" +
                    "WHERE G.group_id = ? AND T.name = ?";
            PreparedStatement prepState;
            prepState = connection.prepareStatement(sql);
            prepState.setString(1, name);
            prepState.setInt(2, groupID);
            ResultSet rs = prepState.executeQuery();
            while (rs.next()) {
                String[] row = new String[colName.length];
                row[0] = rs.getString("Username");
                row[1] = rs.getString("City");
                row[2] = rs.getString("Gender");
                row[3] = rs.getString("Birthdate");
                mem.add(row);
            }

        } catch (Exception e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return mem;
    }


    // update activity's description
    public void updateActivityDescrip (String activityID, String descrip) throws SQLException {
        int intActivityID = Integer.parseInt(activityID);


        System.out.println("view trips in activity id: " + intActivityID);

        try {
            String sql = "UPDATE trav_grp_trp_activity SET Description = ? WHERE Activity_ID = ?";

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, descrip);
            ps.setInt(2, intActivityID);
            ps.executeUpdate();


        } catch (Exception e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

    }


    public double getAverageTripActivities() {
        double result = 0;
        try {
            String sql = "SELECT AVG(count_activities) as TotalAverage FROM\n" +
                    "(SELECT Count(a.ACTIVITY_ID) AS count_activities\n" +
                    " FROM trav_grp_trip t\n" +
                    " LEFT OUTER JOIN trav_grp_trp_activity a ON a.Trip_ID = t.Trip_ID\n" +
                    " GROUP BY t.Trip_ID)\n";
            PreparedStatement ps = connection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
              result = rs.getDouble("TotalAverage");
            }

            rs.close();
            ps.close();

        } catch(SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result;
    }



}
