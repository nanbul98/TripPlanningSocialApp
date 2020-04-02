package ca.ubc.cs304.model;

public class BusinessModel implements UserModel {
    String username;
    String name;
    String country;
    String province;
    String city;


    public BusinessModel(String username, String name, String country, String province, String city, String gender) {
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

