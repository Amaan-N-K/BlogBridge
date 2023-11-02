package org.project.interface_adapters;

import org.springframework.http.ResponseEntity;
import org.project.use_case.AcceptOutputBoundary;
import org.project.use_case.AcceptOutputData;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AcceptPresenter implements AcceptOutputBoundary {
    private boolean success;

    @Override
    public void present(AcceptOutputData outputData) {
        this.success = outputData.isSuccess();
    }

    public ResponseEntity<Object> getResponseEntity() {
        return ResponseEntity.ok(Map.of("success", success));
    }
}
