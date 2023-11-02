package org.project.use_case;

import java.util.List;

public interface DashboardDataAccessInterface {
    int getFriendCount(String username);
    List<String> getFriends(String username);  // Adjust data type as necessary
    List<String> getPosts(String username);   // Adjust data type as necessary
}
