package org.project.use_case.signup;

public interface SignupDataAccessInterface {
    boolean usernameExists(String username);
    void addUser(String username, String password);
}

