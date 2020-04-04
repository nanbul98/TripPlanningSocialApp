package ca.ubc.cs304.model;

import java.time.LocalDate;

public class ActivityModel {
    private final int activityID;
    private final int tripID;
    private final String meetingLoc;
    private final String title;
    private final int cost;
    private final String description;
    private final LocalDate startDate;
    private final LocalDate endDate;


    public ActivityModel(int activityID, int tripID, String meetingLoc, String title,  int cost, String description, LocalDate startDate, LocalDate endDate) {
        this.activityID = activityID;
        this.tripID = tripID;
        this.meetingLoc = meetingLoc;
        this.title = title;
        this.cost = cost;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getActivity() { return activityID; };
    public int getTripID() { return tripID; };
    public String getMeetingLoc() { return meetingLoc; };
    public String getTitle() { return title; };
    public int getCost() { return cost; };
    public String getDescription() { return description; };
    public LocalDate getStartDate() { return startDate; };
    public LocalDate getEndDate() { return endDate; };

}
