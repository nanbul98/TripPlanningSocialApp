package ca.ubc.cs304.model;

public class BusinessModel {
    private final String username;
    private final String name;
    private final String country;
    private final String province;
    private final String city;


    public BusinessModel(String username, String name, String country, String province, String city) {
        this.username = username;
        this.name = name;
        this.country = country;
        this.province = province;
        this.city = city;

    }

    public String getUsername() {
        return username;
    }

    ;

    public String getName() {
        return name;
    }

    ;

    public String getCountry() {
        return country;
    }

    ;

    public String getCity() {
        return city;
    }

    ;

    public String getProvince() {
        return province;
    }

    ;

}

