package ca.ubc.cs304.model;

public class InterestModel {
    private final String name;
    private final String description;


    public InterestModel(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    };
    public String getDescription() {
        return description;
    };

}

