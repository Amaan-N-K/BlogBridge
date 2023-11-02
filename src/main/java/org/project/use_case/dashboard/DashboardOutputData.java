package org.project.use_case;

import java.util.List;

public class DashboardOutputData {
    private final String username;
    private final int friendCount;
    private final List<String> friends;
    private final List<String> posts;

    public DashboardOutputData(String username, int friendCount, List<String> friends, List<String> posts) {
        this.username = username;
        this.friendCount = friendCount;
        this.friends = friends;
        this.posts = posts;
    }

    public String getUsername() {
        return username;
    }

    public int getFriendCount() {
        return friendCount;
    }

    public List<String> getFriends() {
        return friends;
    }

    public List<String> getPosts() {
        return posts;
    }
}
