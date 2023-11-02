package org.project.use_case;

import org.springframework.stereotype.Service;

@Service
public class SignupInteractor implements SignupInputBoundary {

    private final SignupDataAccessInterface dataAccess;
    private final SignupOutputBoundary outputBoundary;

    public SignupInteractor(SignupDataAccessInterface dataAccess, SignupOutputBoundary outputBoundary) {
        this.dataAccess = dataAccess;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void registerUser(SignupInputData signupInputData) {
        boolean userExists = dataAccess.usernameExists(signupInputData.getUsername());
        boolean passwordsMismatch = !signupInputData.getPassword().equals(signupInputData.getConfirmPassword());

        // Create the output data with the results
        SignupOutputData outputData = new SignupOutputData(userExists, passwordsMismatch);
        System.out.println("invoking present");
        outputBoundary.present(outputData);  // Moved outside the if condition

        if (!userExists && !passwordsMismatch) {
            dataAccess.addUser(signupInputData.getUsername(), signupInputData.getPassword());
        }
    }
}
