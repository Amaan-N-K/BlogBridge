package org.project.interface_adapters.login;

import org.project.use_case.login.LoginOutputBoundary;
import org.project.use_case.login.LoginOutputData;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class LoginPresenter implements LoginOutputBoundary {
    private LoginOutputData outputData;

    @Override
    public void setOutputData(LoginOutputData outputData) {
        this.outputData = outputData;
    }

    public ResponseEntity<Object> getResponseEntity() {
        Map<String, Boolean> response = Map.of("success", outputData.isSuccess());
        return ResponseEntity.ok(response);
    }
}
