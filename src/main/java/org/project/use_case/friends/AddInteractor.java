package org.project.use_case;

import org.springframework.stereotype.Service;

@Service
public class AddInteractor implements AddInputBoundary {

    private final AddDataAccessInterface dataAccess;
    private final AddOutputBoundary outputBoundary;

    public AddInteractor(AddDataAccessInterface dataAccess, AddOutputBoundary outputBoundary) {
        this.dataAccess = dataAccess;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void handleRequest(AddInputData inputData) {
        boolean userExists = dataAccess.isUserInDatabase(inputData.getFriendUsername());
        boolean userFriend = dataAccess.isUserInFriends(inputData.getUsername(), inputData.getFriendUsername());
        boolean userFriendRequest = dataAccess.isUserInFriendRequests(inputData.getUsername(), inputData.getFriendUsername());
        if (userExists && !userFriend && !userFriendRequest) {
            dataAccess.addInvitationRequest(inputData.getUsername(), inputData.getFriendUsername());
        }
        outputBoundary.present(new AddOutputData(userExists && !userFriend && !userFriendRequest));
    }
}
