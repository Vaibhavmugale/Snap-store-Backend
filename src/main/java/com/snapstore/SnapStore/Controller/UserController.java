package com.snapstore.SnapStore.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.snapstore.SnapStore.ExceptionHandler.ErrorResponse;
import com.snapstore.SnapStore.ExceptionHandler.ResourceNotFoundException;
import com.snapstore.SnapStore.Request.UserRequest;
import com.snapstore.SnapStore.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signUp")
    public ResponseEntity<String> signUpUser(@RequestBody UserRequest user) {
        return null;

    }

    @PostMapping("/adduser")
    public ResponseEntity<?> addUser(@RequestBody UserRequest user) {
        try {
            System.out.println("user called");
            boolean isCreated = userService.addUser(user);
            if (!isCreated) {
                return new ResponseEntity<>("Email already exists!", HttpStatus.CONFLICT);
            }
            return ResponseEntity.ok(isCreated);
        } catch (ResourceNotFoundException ex) {
            ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value(),
                    "Resource Not Found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            ErrorResponse errorResponse = new ErrorResponse("Internal server error",
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Error");
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getUserById/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Integer id) {
        try {
            UserRequest Users = userService.getUserById(id);
            if (Users.getId() == null && Users.getId() == 0) {
                throw new ResourceNotFoundException("No Users found.");
            }
            return ResponseEntity.ok(Users); 
        } catch (ResourceNotFoundException ex) {
            ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value(), "Resource Not Found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            ErrorResponse errorResponse = new ErrorResponse("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Error");
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

     @GetMapping("/getuser")
    public ResponseEntity<?> getUser() {
        try {
            List<UserRequest> users = userService.getUser();
            if (users.isEmpty()) {
                throw new ResourceNotFoundException("No users found.");
            }
            return ResponseEntity.ok(users); 
        } catch (ResourceNotFoundException ex) {
            ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value(), "Resource Not Found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            ErrorResponse errorResponse = new ErrorResponse("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Error");
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
