package ca.ubc.cs304.delegates;

import java.sql.SQLException;
import java.util.List;

public interface AllGroupsDelegate {
    public List<String[]> viewAllGroups() throws SQLException;
    public List<String[]> getGroupInfo(int Group_ID) throws SQLException;

}