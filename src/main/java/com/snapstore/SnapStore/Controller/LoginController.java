package com.snapstore.SnapStore.Controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.snapstore.SnapStore.Enity.UserEntity;
import com.snapstore.SnapStore.ExceptionHandler.ErrorResponse;
import com.snapstore.SnapStore.ExceptionHandler.ResourceNotFoundException;
import com.snapstore.SnapStore.Request.UserRequest;
import com.snapstore.SnapStore.service.LoginService;
import com.snapstore.SnapStore.service.UserService;
import com.snapstore.SnapStore.utils.JwtUtil;

@RestController
@RequestMapping("login")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private LoginService loginService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody UserRequest user) {
        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(user.getEmailId(), user.getPassword()));

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        UserDetails userdetails;
        try {

            userdetails = loginService.loadUserByUsername(user.getEmailId());
        } 
        catch (ResourceNotFoundException ex) {
            ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value(),
                    "Resource Not Found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            ErrorResponse errorResponse = new ErrorResponse("Internal server error",
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Error");
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        String jwt=jwtUtil.generateToken(userdetails.getUsername());
        UserEntity userEntity = userService.getUserByEmail(user.getEmailId());

    // Create a response object
    Map<String, Object> response = new HashMap<>();
    response.put("token", jwt);
    response.put("user", userEntity);

    return ResponseEntity.ok(response);

    }

}
