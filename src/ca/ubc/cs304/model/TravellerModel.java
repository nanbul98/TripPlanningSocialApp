package ca.ubc.cs304.model;

import java.time.LocalDate;

public class TravellerModel extends UserModel {
    private final String username;
    private final String name;
    private final String country;
    private final String province;
    private final String city;
    private final String gender;
    private final String dob;

    public TravellerModel(String username, String name, String country, String province, String city, String gender, String dob) {
        this.username = username;
        this.name = name;
        this.country = country;
        this.province = province;
        this.city = city;
        this.gender = gender;
        this.dob = dob;
    }

    public String getUsername() {
        return username;
    };
    public String getName() {
        return name;
    };
    public String getCountry() {
        return country;
    };
    public String getCity() {
        return city;
    };
    public String getProvince() {
        return province;
    };
    public String getGender() {
        return gender;
    };
    public String getDOB() { return  dob; };
}

