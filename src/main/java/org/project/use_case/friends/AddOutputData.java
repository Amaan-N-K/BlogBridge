package org.project.use_case.friends;

public class AddOutputData {
    private final boolean userExists;

    public AddOutputData(boolean userExists) {
        this.userExists = userExists;
    }

    public boolean isUserExists() {
        return userExists;
    }
}
