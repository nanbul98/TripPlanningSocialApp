package ca.ubc.cs304.model;

public class GroupModel {
    private final int groupID;
    private final String title;
    private final String description;
    private final String ownerUsername;



    public GroupModel(int groupID, String title, String description, String ownerUsername) {
        this.groupID = groupID;
        this.title = title;
        this.description = description;
        this.ownerUsername = ownerUsername;
    }

    public int getGroupID() { return groupID; }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public String getOwnerUsername() {
        return ownerUsername;
    }

    public Object[] toRowData() {
        return new Object[] {
                title,
                description,
                groupID,
                ownerUsername
        };
    }
}

