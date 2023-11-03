package org.project.use_case.friends;

public interface AddDataAccessInterface {
    boolean isUserInDatabase(String username);

    boolean isUserInFriendRequests(String username, String friendUsername);

    boolean isUserInFriends(String username, String friendUsername);
    void addInvitationRequest(String username, String friendUsername);
}