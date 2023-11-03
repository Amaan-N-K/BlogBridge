package org.project.use_case.login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginInteractor implements LoginInputBoundary {

    private final LoginDataAccessInterface dataAccess;
    private final LoginOutputBoundary outputBoundary;

    @Autowired
    public LoginInteractor(LoginDataAccessInterface dataAccess, LoginOutputBoundary outputBoundary) {
        this.dataAccess = dataAccess;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public boolean loginUser(LoginInputData inputData) {
        if(dataAccess.doesUsernameExist(inputData.getUsername())) {
            if(dataAccess.isPasswordCorrect(inputData.getUsername(), inputData.getPassword())) {
                outputBoundary.setOutputData(new LoginOutputData(true));
                return true;
            }
        }
        outputBoundary.setOutputData(new LoginOutputData(false));
        return false;
    }
}
