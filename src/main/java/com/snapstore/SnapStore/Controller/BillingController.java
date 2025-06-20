package com.snapstore.SnapStore.Controller;

import com.snapstore.SnapStore.ExceptionHandler.ErrorResponse;
import com.snapstore.SnapStore.ExceptionHandler.ResourceNotFoundException;
import com.snapstore.SnapStore.Request.BillingRequest;
import com.snapstore.SnapStore.service.BillingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/billing")
public class BillingController {

    @Autowired
    private BillingService billingService;

    @GetMapping("/getbilling/{userId}")
    public ResponseEntity<?> getBilling(@PathVariable Integer userId) {
        try {
            List<BillingRequest> billings = billingService.getBilling(userId);
            if (billings.isEmpty()) {
                throw new ResourceNotFoundException("No billings found.");
            }
            return ResponseEntity.ok(billings); 
        } catch (ResourceNotFoundException ex) {
            ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value(), "Resource Not Found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            ex.printStackTrace();
            ErrorResponse errorResponse = new ErrorResponse("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Error");
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getBillingById/{id}")
    public ResponseEntity<?> getBillingById(@PathVariable Integer id) {
        try {
            BillingRequest billings = billingService.getBillingById(id);
            if (billings.getId() == null && billings.getId() == 0) {
                throw new ResourceNotFoundException("No billings found.");
            }
            return ResponseEntity.ok(billings); 
        } catch (ResourceNotFoundException ex) {
            ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value(), "Resource Not Found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
             ex.printStackTrace();
            ErrorResponse errorResponse = new ErrorResponse("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Error");
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addBilling(@RequestBody BillingRequest billing) {
        try {
            if (billing == null) {
                throw new IllegalArgumentException("Billing data is invalid");
            }
            BillingRequest addedBilling = billingService.addBilling(billing);
            return ResponseEntity.status(HttpStatus.CREATED).body(addedBilling); 
        } catch (IllegalArgumentException ex) {
            ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value(), "Bad Request");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            ex.printStackTrace();
            ErrorResponse errorResponse = new ErrorResponse("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Error");
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
