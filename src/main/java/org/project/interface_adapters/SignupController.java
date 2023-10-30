package org.project.interface_adapters;

import org.project.use_case.SignupInputData;
import org.project.use_case.SignupInputBoundary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import org.springframework.http.ResponseEntity;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/signup")
public class SignupController {

    private final SignupInputBoundary signupInputBoundary;
    private final SignupPresenter signupPresenter; // Injecting SignupPresenter

    @Autowired
    public SignupController(SignupInputBoundary signupInputBoundary, SignupPresenter signupPresenter) {
        this.signupInputBoundary = signupInputBoundary;
        this.signupPresenter = signupPresenter;
    }

    @PostMapping
    public ResponseEntity<Object> signup(@RequestBody Map<String, Object> userData) {
        try {
            System.out.println("Received userData: " + userData);
            SignupInputData inputData = new SignupInputData(
                    userData.get("username").toString(),
                    userData.get("password").toString(),
                    userData.get("confirmPassword").toString()
            );
            signupInputBoundary.registerUser(inputData);
            return signupPresenter.getResponseEntity();  // Returning the ResponseEntity from the presenter
        } catch (Exception e) {
            System.out.println("Error receiving signup data: " + e.getMessage());
            return ResponseEntity.badRequest().body("Error processing request.");  // Return an error ResponseEntity
        }
    }
}
