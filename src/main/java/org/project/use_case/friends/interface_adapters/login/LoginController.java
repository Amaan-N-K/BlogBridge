package org.project.interface_adapters.login;

import org.project.use_case.login.LoginInputData;
import org.project.use_case.login.LoginInputBoundary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final LoginInputBoundary loginInputBoundary;
    private final LoginPresenter loginPresenter;

    @Autowired
    public LoginController(LoginInputBoundary loginInputBoundary, LoginPresenter loginPresenter) {
        this.loginInputBoundary = loginInputBoundary;
        this.loginPresenter = loginPresenter;
    }
    @GetMapping
    public ResponseEntity<Resource> serveLoginPage() {
        Resource resource = new ClassPathResource("static/login.html");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_HTML);
        try {
            return new ResponseEntity<>(resource, headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping
    public ResponseEntity<Object> login(@RequestBody Map<String, String> userData) {
        try {
            LoginInputData inputData = new LoginInputData(
                    userData.get("username"),
                    userData.get("password")
            );
            // Handle the login
            loginInputBoundary.loginUser(inputData);

            // Get the response from the LoginPresenter
            System.out.println(loginPresenter.getResponseEntity());
            return loginPresenter.getResponseEntity();

        } catch (Exception e) {
            System.out.println("catch");
            System.out.println("Error receiving login data: " + e.getMessage());
            return ResponseEntity.badRequest().body("Error processing request.");
        }
    }
}
