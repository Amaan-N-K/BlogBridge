package org.project.interface_adapters.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.project.use_case.dashboard.DashboardBoundary;


@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    private final DashboardBoundary dashboardBoundary;
    private final DashboardPresenter dashboardPresenter; // Injecting DashboardPresenter

    @Autowired
    public DashboardController(DashboardBoundary dashboardBoundary, DashboardPresenter dashboardPresenter) {
        this.dashboardBoundary = dashboardBoundary;
        this.dashboardPresenter = dashboardPresenter;
    }

    @GetMapping
    public ResponseEntity<Object> fetchDashboardData(@RequestParam("username") String username) {
        try {
            System.out.println("Fetching data for user: " + username);
            dashboardBoundary.fetchDataForUser(username);

            System.out.println(dashboardPresenter.getResponseEntity());
            return dashboardPresenter.getResponseEntity();  // Returning the ResponseEntity from the presenter
        } catch (Exception e) {
            System.out.println("Error fetching dashboard data: " + e.getMessage());
            return ResponseEntity.badRequest().body("Error processing request.");  // Return an error ResponseEntity
        }
    }

}
