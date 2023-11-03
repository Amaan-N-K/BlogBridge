package org.project.use_case.login;

public class LoginOutputData {
    private final boolean success;

    public LoginOutputData(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }
}
