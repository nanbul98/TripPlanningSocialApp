package ca.ubc.cs304.delegates;

import java.util.List;

public interface ForumPostDelegate {
    public List<String[]> getForumPosts(boolean postID, boolean title, boolean body, boolean timestamp, boolean author, boolean tripID, String tripIDNum);
}
