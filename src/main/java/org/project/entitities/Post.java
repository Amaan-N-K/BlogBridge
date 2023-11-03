package org.project.entitities;

public class Post {

    private final String username;
    private final String title;
    private final String body;

    public Post(String username, String title, String body) {
        this.username = username;
        this.title = title;
        this.body = body;
    }

    public String getUsername() {
        return username;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "Post{" +
                "username='" + username + '\'' +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
