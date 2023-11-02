package org.project.interface_adapters;

import org.project.use_case.signup.SignupOutputData;
import org.project.use_case.signup.SignupOutputBoundary;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.HashMap;
@Service
public class SignupPresenter implements SignupOutputBoundary {

    private ResponseEntity<Object> responseEntity;

    @Override
    public void present(SignupOutputData outputData) {
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("userExists", outputData.isUserExists());
        responseData.put("passwordsMismatch", outputData.isPasswordsMismatch());
        System.out.println(responseData);
        responseEntity = ResponseEntity.ok(responseData);
    }

    // Get the ResponseEntity to be sent as the HTTP response
    public ResponseEntity<Object> getResponseEntity() {
        return responseEntity;
    }
}
