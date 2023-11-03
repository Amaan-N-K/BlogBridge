package org.project.use_case.friends;

public class AcceptInputData {
    private String username; // The currently logged-in user
    private String friendUsername; // The user whose friend request is being accepted

    public AcceptInputData(String username, String friendUsername) {
        this.username = username;
        this.friendUsername = friendUsername;
    }

    public String getUsername() {
        return username;
    }

    public String getFriendUsername() {
        return friendUsername;
    }
}
