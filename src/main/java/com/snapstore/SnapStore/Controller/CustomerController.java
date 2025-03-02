package com.snapstore.SnapStore.Controller;

import com.snapstore.SnapStore.ExceptionHandler.ErrorResponse;
import com.snapstore.SnapStore.ExceptionHandler.ResourceNotFoundException;
import com.snapstore.SnapStore.Request.CustomerRequest;
import com.snapstore.SnapStore.service.CustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/getcustomer/{userId}")
    public ResponseEntity<?> getCustomer(@PathVariable Integer userId) {
        try {
            List<CustomerRequest> customers = customerService.getCustomer(userId);
            if (customers.isEmpty()) {
                throw new ResourceNotFoundException("No customers found.");
            }
            return ResponseEntity.ok(customers); 
        } catch (ResourceNotFoundException ex) {
            ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value(), "Resource Not Found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            ErrorResponse errorResponse = new ErrorResponse("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Error");
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getCustomerById/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable Integer id) {
        try {
            CustomerRequest customers = customerService.getCustomerById(id);
            if (customers.getId() == null && customers.getId() == 0) {
                throw new ResourceNotFoundException("No customers found.");
            }
            return ResponseEntity.ok(customers); 
        } catch (ResourceNotFoundException ex) {
            ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value(), "Resource Not Found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            ErrorResponse errorResponse = new ErrorResponse("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Error");
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCustomer(@RequestBody CustomerRequest customer) {
        try {
            if (customer == null) {
                throw new IllegalArgumentException("Customer data is invalid");
            }
            CustomerRequest addedCustomer = customerService.addCustomer(customer);
            return ResponseEntity.status(HttpStatus.CREATED).body(addedCustomer); 
        } catch (IllegalArgumentException ex) {
            ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value(), "Bad Request");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            ErrorResponse errorResponse = new ErrorResponse("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Error");
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
