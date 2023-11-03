package org.project.use_case.dashboard;

import org.project.entitities.Post;

import java.util.List;

public interface DashboardDataAccessInterface {
    int getFriendCount(String username);
    List<String> getFriends(String username);  // Adjust data type as necessary
    List<Post> getPosts(String username);   // Adjust data type as necessary
    List<String> getFriendRequests(String username);
}
