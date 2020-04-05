package ca.ubc.cs304.delegates;

import java.sql.SQLException;
import java.util.List;

public interface AllGroupsDelegate {
    public List<String[]> viewAllGroups() throws SQLException;
    public List<String[]> getGroupInfo(String groupTitle) throws SQLException;
    public int countAllMember (String groupID) throws SQLException;
    public List<String[]> viewAllGroupMembers(String groupID);
    public List<String[]> findSuperStar(String groupID) throws SQLException;
    public List<String[]> viewGroupTrips(String groupID) throws SQLException;
    public List<String[]> viewTripActivity(String groupID, String tripID) throws SQLException;
    public List<String[]> findTripWithAllFreeAct() throws SQLException;


}
