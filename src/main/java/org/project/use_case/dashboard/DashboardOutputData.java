package org.project.use_case.dashboard;

import org.project.entitities.Post;

import java.util.List;

public class DashboardOutputData {
    private final String username;
    private final int friendCount;
    private final List<String> friends;
    private final List<Post> posts;
    private final List<String> friendRequests;

    public DashboardOutputData(String username, int friendCount, List<String> friends, List<Post> posts, List<String> friendRequests) {
        this.username = username;
        this.friendCount = friendCount;
        this.friends = friends;
        this.posts = posts;
        this.friendRequests = friendRequests;
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

    public List<Post> getPosts() {
        return posts;
    }

    public List<String> getFriendRequests() {return friendRequests;}
}
