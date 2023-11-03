package org.project.use_case.friends;

import org.springframework.stereotype.Service;

@Service
public class AcceptInteractor implements AcceptInputBoundary {
    private final AcceptDataAccessInterface dataAccess;
    private final AcceptOutputBoundary presenter;

    public AcceptInteractor(AcceptDataAccessInterface dataAccess, AcceptOutputBoundary presenter) {
        this.dataAccess = dataAccess;
        this.presenter = presenter;
    }

    @Override
    public void handleRequest(AcceptInputData inputData) {
        boolean isSuccess = dataAccess.acceptFriend(inputData.getUsername(), inputData.getFriendUsername());
        presenter.present(new AcceptOutputData(isSuccess));
    }
}
