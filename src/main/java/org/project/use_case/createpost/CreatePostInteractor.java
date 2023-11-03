package org.project.use_case.createpost;

import org.springframework.stereotype.Service;

@Service
public class CreatePostInteractor implements CreatePostInputBoundary {
    private final CreatePostDataAccessInterface dataAccess;
    private final CreatePostOutputBoundary outputBoundary;

    public CreatePostInteractor(CreatePostDataAccessInterface dataAccess, CreatePostOutputBoundary outputBoundary) {
        this.dataAccess = dataAccess;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void createPost(CreatePostInputData inputData) {
        dataAccess.addPost(inputData.getUsername(), inputData.getTitle(), inputData.getBody());
        CreatePostOutputData outputData = new CreatePostOutputData(true);
        outputBoundary.present(outputData);
    }
}
