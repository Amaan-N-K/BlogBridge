package org.project.use_case.login;

public interface LoginDataAccessInterface {
    boolean doesUsernameExist(String username);
    boolean isPasswordCorrect(String username, String password);
}

