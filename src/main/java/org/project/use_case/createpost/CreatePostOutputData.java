package org.project.use_case.createpost;

public class CreatePostOutputData {
    private boolean success;

    public CreatePostOutputData (boolean success) {
        this.success = success;
    }
    public boolean isSuccess() {
        return this.success;
    }

    // Setter (assuming you already have this)
}

