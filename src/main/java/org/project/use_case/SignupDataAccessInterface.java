package org.project.use_case;

public interface SignupDataAccessInterface {
    boolean usernameExists(String username);
    void addUser(String username, String password);
}

