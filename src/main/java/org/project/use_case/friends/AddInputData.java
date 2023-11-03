package org.project.use_case.friends;

public class AddInputData {
    private final String username;
    private final String friendUsername;

    public AddInputData(String username, String friendUsername) {
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