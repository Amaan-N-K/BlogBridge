package org.project.interface_adapters;

import org.project.use_case.AddOutputBoundary;
import org.project.use_case.AddOutputData;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AddPresenter implements AddOutputBoundary {
    private ResponseEntity<Object> responseEntity;

    @Override
    public void present(AddOutputData outputData) {
        if (outputData.isUserExists()) {
            responseEntity = ResponseEntity.ok().body(Map.of("success", true));
        } else {
            responseEntity = ResponseEntity.ok().body(Map.of("success", false));
        }
    }

    public ResponseEntity<Object> getResponseEntity() {
        return responseEntity;
    }
}
