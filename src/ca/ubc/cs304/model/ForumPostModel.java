package ca.ubc.cs304.model;

import java.sql.Timestamp;

public class ForumPostModel {

    private final int postID;
    private final String title;
    private final String body;
    private final String author;
    private final String tripID;
    private final Timestamp timestamp;


    public ForumPostModel(int postID, String title,String body, String author,String tripID, Timestamp timestamp) {
        this.postID = postID;
        this.title = title;
        this.body = body;
        this.author = author;
        this.tripID = tripID;
        this.timestamp = timestamp;
    }

    public int getPostID() {
        return postID;
    };
    public String getTitle() {
        return title;
    };
    public String getBody() {
        return body;
    };
    public String getAuthor() {
        return author;
    };
    public String getTripID() {
        return tripID;
    };

    public Timestamp getTimestamp() {
        return timestamp;
    }
}
