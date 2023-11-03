package org.project.data_access;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import org.project.entitities.Post;
import org.project.use_case.createpost.CreatePostDataAccessInterface;
import org.project.use_case.friends.AcceptDataAccessInterface;
import org.project.use_case.friends.AddDataAccessInterface;
import org.project.use_case.login.LoginDataAccessInterface;
import org.project.use_case.signup.SignupDataAccessInterface;
import org.springframework.stereotype.Repository;
import org.project.use_case.dashboard.DashboardDataAccessInterface;

@Repository
public class DataAccessObject implements SignupDataAccessInterface, LoginDataAccessInterface,
        DashboardDataAccessInterface, AddDataAccessInterface, AcceptDataAccessInterface, CreatePostDataAccessInterface {
    private static final String DB_PATH = "jdbc:sqlite:users.db";

    public DataAccessObject() {
        try (Connection connection = DriverManager.getConnection(DB_PATH);
             Statement statement = connection.createStatement()) {

            // User table creation (without bio column)
            String createUserTableSQL = "CREATE TABLE IF NOT EXISTS users (" +
                    "username TEXT PRIMARY KEY NOT NULL," +
                    "password TEXT NOT NULL," +
                    "friends TEXT DEFAULT 'none'," +
                    "friend_requests TEXT DEFAULT 'none'" +
                    ")";
            statement.execute(createUserTableSQL);

            // Post table creation
            String createPostTableSQL = "CREATE TABLE IF NOT EXISTS posts (" +
                    "post_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "username TEXT NOT NULL," +
                    "title TEXT NOT NULL," +
                    "body TEXT NOT NULL," +
                    "FOREIGN KEY(username) REFERENCES users(username)" +
                    ")";
            statement.execute(createPostTableSQL);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addUser(String username, String password) {
        String insertSQL = "INSERT INTO users(username, password) VALUES(?, ?)";
        try (Connection connection = DriverManager.getConnection(DB_PATH);
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean usernameExists(String username) {
        String querySQL = "SELECT COUNT(*) AS count FROM users WHERE username = ?";
        try (Connection connection = DriverManager.getConnection(DB_PATH);
             PreparedStatement preparedStatement = connection.prepareStatement(querySQL)) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("count") > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean doesUsernameExist(String username) {
        return usernameExists(username);
    }

    @Override
    public boolean isPasswordCorrect(String username, String password) {
        String querySQL = "SELECT password FROM users WHERE username = ?";
        try (Connection connection = DriverManager.getConnection(DB_PATH);
             PreparedStatement preparedStatement = connection.prepareStatement(querySQL)) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("password").equals(password);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public int getFriendCount(String username) {
        String querySQL = "SELECT friends FROM users WHERE username = ?";
        try (Connection connection = DriverManager.getConnection(DB_PATH);
             PreparedStatement preparedStatement = connection.prepareStatement(querySQL)) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String friendsList = resultSet.getString("friends");
                    if (friendsList != null && !friendsList.equals("none")) {
                        return friendsList.split(",").length;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<String> getFriends(String username) {
        String querySQL = "SELECT friends FROM users WHERE username = ?";
        List<String> friendList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_PATH);
             PreparedStatement preparedStatement = connection.prepareStatement(querySQL)) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String friends = resultSet.getString("friends");
                    if (friends != null && !friends.equals("none")) {
                        friendList = Arrays.asList(friends.split(","));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return friendList;
    }

    @Override
    public List<Post> getPosts(String username) {
        List<Post> posts = new ArrayList<>();

        String getFriendsSQL = "SELECT friends FROM users WHERE username = ?";
        String getPostsSQL = "SELECT username, title, body FROM posts WHERE username IN (?,?)";

        try (Connection connection = DriverManager.getConnection(DB_PATH);
             PreparedStatement friendsStatement = connection.prepareStatement(getFriendsSQL);
             PreparedStatement postsStatement = connection.prepareStatement(getPostsSQL)) {

            friendsStatement.setString(1, username);
            ResultSet friendsSet = friendsStatement.executeQuery();

            if (friendsSet.next()) {
                String friends = friendsSet.getString("friends");
                List<String> friendList = new ArrayList<>(Arrays.asList(friends.split(",")));
                friendList.add(username); // To also get posts of the given user

                // Note: The below approach is naive as it gets posts one by one for each user.
                // A more advanced approach would be to use dynamic SQL or UNION for batch retrieval.

                for (String user : friendList) {
                    postsStatement.setString(1, user);
                    ResultSet postSet = postsStatement.executeQuery();
                    while (postSet.next()) {
                        Post post = new Post(
                                postSet.getString("username"),
                                postSet.getString("title"),
                                postSet.getString("body")
                        );
                        posts.add(post);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return posts;
    }

    @Override
    public void addPost(String username, String title, String body) {
        String insertSQL = "INSERT INTO posts(username, title, body) VALUES(?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(DB_PATH);
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, title);
            preparedStatement.setString(3, body);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> getFriendRequests(String username) {
        String querySQL = "SELECT friend_requests FROM users WHERE username = ?";
        List<String> friendRequestList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_PATH);
             PreparedStatement preparedStatement = connection.prepareStatement(querySQL)) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String friendRequests = resultSet.getString("friend_requests");
                    if (friendRequests != null && !friendRequests.equals("none")) {
                        friendRequestList = Arrays.asList(friendRequests.split(","));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return friendRequestList;
    }

    @Override
    public boolean isUserInDatabase(String username) {
        return usernameExists(username);
    }

    @Override
    public boolean isUserInFriendRequests(String username, String friendUsername) {
        String querySQL = "SELECT friend_requests FROM users WHERE username = ?";
        try (Connection connection = DriverManager.getConnection(DB_PATH);
             PreparedStatement preparedStatement = connection.prepareStatement(querySQL)) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String friendRequests = resultSet.getString("friend_requests");
                    if (friendRequests != null && !friendRequests.equals("none")) {
                        List<String> friendRequestList = Arrays.asList(friendRequests.split(","));
                        return friendRequestList.contains(friendUsername);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean isUserInFriends(String username, String friendUsername) {
        String querySQL = "SELECT friends FROM users WHERE username = ?";
        try (Connection connection = DriverManager.getConnection(DB_PATH);
             PreparedStatement preparedStatement = connection.prepareStatement(querySQL)) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String friends = resultSet.getString("friends");
                    if (friends != null && !friends.equals("none")) {
                        List<String> friendList = Arrays.asList(friends.split(","));
                        return friendList.contains(friendUsername);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void addInvitationRequest(String username, String friendUsername) {
        String updateSQL = "UPDATE users SET friend_requests = CASE WHEN friend_requests = 'none' THEN ? ELSE friend_requests || ',' || ? END WHERE username = ?";
        try (Connection connection = DriverManager.getConnection(DB_PATH);
             PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, friendUsername);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean acceptFriend(String username, String friendUsername) {
        // Add each user to the other's friend list
        String updateFriendsSQL = "UPDATE users SET friends = CASE WHEN friends = 'none' THEN ? ELSE friends || ',' || ? END WHERE username = ?";
        // Remove friendUsername from username's friend requests
        String updateFriendRequestsSQL = "UPDATE users SET friend_requests = " +
                "CASE " +
                "WHEN friend_requests = ? THEN 'none' " + // only friendUsername in the list
                "WHEN friend_requests LIKE ? THEN REPLACE(friend_requests, ',' || ?, '') " + // friendUsername at the end
                "WHEN friend_requests LIKE ? THEN REPLACE(friend_requests, ?, ',') " + // friendUsername at the start
                "ELSE REPLACE(friend_requests, ',' || ? || ',', ',') " + // friendUsername in the middle
                "END WHERE username = ?";

        try (Connection connection = DriverManager.getConnection(DB_PATH);
             PreparedStatement updateFriendsStmt = connection.prepareStatement(updateFriendsSQL);
             PreparedStatement updateFriendRequestsStmt = connection.prepareStatement(updateFriendRequestsSQL)) {

            // Update friends for the user and friend
            updateFriendsStmt.setString(1, friendUsername);
            updateFriendsStmt.setString(2, friendUsername);
            updateFriendsStmt.setString(3, username);
            updateFriendsStmt.executeUpdate();

            updateFriendsStmt.setString(1, username);
            updateFriendsStmt.setString(2, username);
            updateFriendsStmt.setString(3, friendUsername);
            updateFriendsStmt.executeUpdate();

            // Remove friendUsername from username's friend_requests
            updateFriendRequestsStmt.setString(1, friendUsername);
            updateFriendRequestsStmt.setString(2, friendUsername + ',');
            updateFriendRequestsStmt.setString(3, friendUsername);
            updateFriendRequestsStmt.setString(4, friendUsername + ',');
            updateFriendRequestsStmt.setString(5, friendUsername);
            updateFriendRequestsStmt.setString(6, friendUsername);
            updateFriendRequestsStmt.setString(7, username);
            updateFriendRequestsStmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}