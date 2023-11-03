package org.project.use_case.createpost;

public class CreatePostInputData {
    private final String username;
    private final String title;
    private final String body;

    public CreatePostInputData(String username, String title, String body) {
        this.username = username;
        this.title = title;
        this.body = body;
    }

    // Getters
    public String getUsername() {
        return this.username;
    }

    public String getTitle() {
        return this.title;
    }

    public String getBody() {
        return this.body;
    }

}
