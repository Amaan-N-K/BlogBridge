package org.project.use_case.signup;

public class SignupInputData {
    private final String username;
    private final String password;
    private final String confirmPassword;

    public SignupInputData(String username, String password, String confirmPassword) {
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }
}
