package ca.ubc.cs304.delegates;

import ca.ubc.cs304.model.GroupModel;
import ca.ubc.cs304.model.InterestModel;

public interface InterestListWindowDelegate {
    InterestModel[] getAllInterests();
    GroupModel[] findGroupsWithInterest(String interestName);
    void goToInterestGroupsPage(GroupModel[] groupResults);
}
