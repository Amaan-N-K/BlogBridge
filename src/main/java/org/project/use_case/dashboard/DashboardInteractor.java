package org.project.use_case;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardInteractor implements DashboardBoundary {

    private final DashboardDataAccessInterface dashboardDataAccess;
    private final DashboardOutputBoundary dashboardOutputBoundary;

    @Autowired
    public DashboardInteractor(DashboardDataAccessInterface dashboardDataAccess,
                               DashboardOutputBoundary dashboardOutputBoundary) {
        this.dashboardDataAccess = dashboardDataAccess;
        this.dashboardOutputBoundary = dashboardOutputBoundary;
    }

    @Override
    public void fetchDataForUser(String username) {
        // Fetching data from data access layer
        int friendCount = dashboardDataAccess.getFriendCount(username);
        List<String> friends = dashboardDataAccess.getFriends(username);
        List<String> posts = dashboardDataAccess.getPosts(username);

        // Constructing output data
        DashboardOutputData data = new DashboardOutputData(username, friendCount, friends, posts);

        dashboardOutputBoundary.present(data);
    }
}
