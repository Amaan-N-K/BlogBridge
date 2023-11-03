package org.project.use_case.signup;

public class SignupOutputData {
    private final boolean userExists;
    private final boolean passwordsMismatch;

    public SignupOutputData(boolean userExists, boolean passwordsMismatch) {
        this.userExists = userExists;
        this.passwordsMismatch = passwordsMismatch;
    }

    public boolean isUserExists() {
        return userExists;
    }

    public boolean isPasswordsMismatch() {
        return passwordsMismatch;
    }
}
