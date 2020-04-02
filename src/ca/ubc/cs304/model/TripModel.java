package ca.ubc.cs304.model;

import java.time.LocalDate;

public class TripModel {
    private final int tripID;
    private final int groupID;
    private final String country;
    private final String province;
    private final String city;
    private final String title;
    private final int groupMax;
    private final int cost;
    private final String currency;
    private final String description;
    private final LocalDate startDate;
    private final LocalDate endDate;


    public TripModel(int tripID, int groupID, String country, String province,  String city, String title, int groupMax,
                     int cost, String currency, String description, LocalDate startDate, LocalDate endDate) {
        this.tripID = tripID;
        this.groupID = groupID;
        this.country = country;
        this.province = province;
        this.city = city;
        this.title = title;
        this.groupMax = groupMax;
        this.cost = cost;
        this.currency = currency;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getTripID() { return tripID; };
    public int getGroupID() { return groupID; };
    public String getCountry() { return country; };
    public String getProvince() { return province; };
    public String getCity() { return city; };
    public String getTitle() { return title; };
    public int getGroupMax() { return groupMax; };
    public int getCost() { return cost; };
    public String getCurrency() { return currency; };
    public String getDescription() { return description; };
    public LocalDate getStartDate() { return startDate; };
    public LocalDate getEndDate() { return endDate; };

}
