package ca.ubc.cs304.database;


import ca.ubc.cs304.model.GroupModel;
import ca.ubc.cs304.model.TravellerModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//import ca.ubc.cs304.model.UserModel;

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

    public void databaseSetup() {

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
        String[] colName = {"Username","Name","Country","Province","City","Gender","Date of Birth"};
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
                row[6] = rs.getString("Date of Birth");
                res.add(row);
            }
        } catch (SQLException e) {
            rollbackConnection();
            throw e;
        }
        return res;
    }


    public void updateActivity(int id, String description) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE trav_grp_trp_activity SET Description = ? WHERE Activity_ID = ?");
            ps.setInt(1, id);
            ps.setString(2, description);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                System.out.println(WARNING_TAG + " Activity " + id + " does not exist!");
            }

            connection.commit();

            ps.close();

        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }


    public TravellerModel[] getTravellerInfoBasedOnTitle(String title) {
        ArrayList<TravellerModel> result = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM traveller WHERE name = ?");
            ps.setString(1,title);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                TravellerModel model = new TravellerModel(rs.getString("username"),
                        rs.getString("name"),
                        rs.getString("country"),
                        rs.getString("province"),
                        rs.getString("city"),
                        rs.getString("gender"),
                        rs.getString("birthdate"));
                result.add(model);
            }

            rs.close();
            ps.close();

        } catch(SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new TravellerModel[result.size()]);
    }

    public GroupModel[] getGroupsBasedOnInterest(String keyword) {
        ArrayList<GroupModel> result = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT tg.Title, tg.Description, tg.Group_ID, tg.Owner_Username FROM traveller_group tg, trav_group_interests tgi WHERE tgi.Group_ID = tg.Group_ID AND tgi.Interest_Name LIKE ?" +
                    "UNION" + "SELECT bg.Title, bg.Description, bg.Group_ID, bg.Owner_Username FROM business_group tg, bus_group_interests bgi WHERE bgi.Group_ID = bg.Group_ID AND bgi.Interest_Name LIKE %" + "?" + "%");
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


    public int getAverageTripActivitiesPerGroup() {
        int result = 0;
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT AVG(TripActivity.count_activities) AS TotalAverage FROM (SELECT Count(*) AS count_activities FROM trav_grp_trp_activity GROUP BY Trip_ID ) AS TripActivity");

            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
              result = rs.getInt("TotalAverage");
            }

            rs.close();
            ps.close();

        } catch(SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result;
    }

    public int countAllGroupMember (int groupID) {
        int count = 0;

        try {
            String sql = "SELECT COUNT(*) AS total \n" +
                         "FROM traveller T, trav_group_member_travellers G\n" +
                         "WHERE T.username = G.username\n" +
                         "GROUP BY G.group_id\n" +
                         "HAVING G.group_id = ?";

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


    public List<String[]> findStarMember (int groupID) {
        List<String[]> mem = new ArrayList<>();
        String[] colName = {"Username", "Name", "Location", "Gender", "Birthdate"};
        mem.add(colName);
        try {
            String sql = "CREATE VIEW trips_from_specific_group AS\n" +
                    "SELECT * FROM trav_grp_trip WHERE group_ID";
            String sql2 = "SELECT u.name FROM traveller u WHERE not exists\n" +
                          "(SELECT * from trips_from_specific_group t where not exists\n" +
                          "(SELECT g.username FROM TRAV_GROUP_MEMBER_TRAVELLERS g where u.username = g.username AND t.Group_ID  = g.Group_ID))";

        // TODO need to finish the code later

        } catch (Exception e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return mem;
    }




}
