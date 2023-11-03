package org.project.interface_adapters.dashboard;

import org.project.use_case.dashboard.DashboardOutputBoundary;
import org.project.use_case.dashboard.DashboardOutputData;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class DashboardPresenter implements DashboardOutputBoundary {

    private ResponseEntity<Object> responseEntity;

    @Override
    public void present(DashboardOutputData data) {
        Map<String, Object> responseData = new HashMap<>();

        // Convert DashboardOutputData to a Map
        responseData.put("username", data.getUsername());
        responseData.put("friendCount", data.getFriendCount());
        responseData.put("friends", data.getFriends());

        // Convert each Post object to a Map and collect them into a List
        List<Map<String, String>> posts = data.getPosts().stream().map(post -> {
            Map<String, String> postMap = new HashMap<>();
            postMap.put("username", post.getUsername());
            postMap.put("title", post.getTitle());
            postMap.put("body", post.getBody());
            return postMap;
        }).collect(Collectors.toList());

        responseData.put("posts", posts);

        responseData.put("friendRequests", data.getFriendRequests());
        System.out.println("here");
        responseEntity = ResponseEntity.ok(responseData);
    }



    // Get the ResponseEntity to be sent as the HTTP response
    public ResponseEntity<Object> getResponseEntity() {
        return responseEntity;
    }
}
