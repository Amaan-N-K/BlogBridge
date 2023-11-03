package org.project.interface_adapters.createpost;

import org.project.use_case.createpost.CreatePostInputBoundary;
import org.project.use_case.createpost.CreatePostInputData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/createPost")
public class CreatePostController {

    private final CreatePostInputBoundary createPostInputBoundary;
    private final CreatePostPresenter createPostPresenter; // Injecting CreatePostPresenter

    @Autowired
    public CreatePostController(CreatePostInputBoundary createPostInputBoundary, CreatePostPresenter createPostPresenter) {
        this.createPostInputBoundary = createPostInputBoundary;
        this.createPostPresenter = createPostPresenter;
    }

    @PostMapping
    public ResponseEntity<Object> createPost(@RequestBody Map<String, Object> postData) {
        try {
            System.out.println("Received postData: " + postData);
            CreatePostInputData inputData = new CreatePostInputData(
                    postData.get("username").toString(),
                    postData.get("title").toString(),
                    postData.get("body").toString()
            );
            createPostInputBoundary.createPost(inputData);
            return createPostPresenter.getResponseEntity();  // Returning the ResponseEntity from the presenter
        } catch (Exception e) {
            System.out.println("Error receiving post data: " + e.getMessage());
            return ResponseEntity.badRequest().body("Error processing request.");  // Return an error ResponseEntity
        }
    }
}
