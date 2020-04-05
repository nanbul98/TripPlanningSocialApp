package ca.ubc.cs304.delegates;

import java.sql.SQLException;
import java.util.List;

public interface AllTravellersDelegate {
    void goAddNewTraveller();
    void goDeleteTraveller();
    void goToMainWindow();
    public List<String[]> viewAllUsers() throws SQLException;

}
