package org.project.interface_adapters;

import org.project.use_case.AcceptInteractor;
import org.project.use_case.AcceptInputData;
import org.project.use_case.AddInputBoundary;
import org.project.use_case.AddInputData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/friends")
public class FriendController {

    @Autowired
    private AddInputBoundary addFriendInteractor;
    @Autowired
    private AddPresenter addFriendPresenter;
    @Autowired
    private AcceptInteractor acceptInteractor;
    @Autowired
    private AcceptPresenter acceptPresenter;


    @PostMapping("/add")
    public ResponseEntity<Object> addFriend(@RequestBody Map<String, String> payload) {
        AddInputData inputData = new AddInputData(payload.get("username"), payload.get("friendUsername"));
        addFriendInteractor.handleRequest(inputData);

        System.out.println(addFriendPresenter.getResponseEntity());
        return addFriendPresenter.getResponseEntity();
    }

    @PostMapping("/accept")
    public ResponseEntity<Object> acceptFriend(@RequestBody Map<String, String> payload) {
        AcceptInputData inputData = new AcceptInputData(payload.get("username"), payload.get("friendUsername"));
        acceptInteractor.handleRequest(inputData);
        return acceptPresenter.getResponseEntity();
    }


}