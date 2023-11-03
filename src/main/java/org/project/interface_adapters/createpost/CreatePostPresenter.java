package org.project.interface_adapters.createpost;

import org.springframework.http.ResponseEntity;
import org.project.use_case.createpost.CreatePostOutputBoundary;
import org.project.use_case.createpost.CreatePostOutputData;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CreatePostPresenter implements CreatePostOutputBoundary {
    private boolean success;

    @Override
    public void present(CreatePostOutputData outputData) {
        this.success = outputData.isSuccess();
    }

    public ResponseEntity<Object> getResponseEntity() {
        if (success) {
            return ResponseEntity.ok(Map.of("success", true));
        } else {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Post creation failed"));
        }
    }
}
