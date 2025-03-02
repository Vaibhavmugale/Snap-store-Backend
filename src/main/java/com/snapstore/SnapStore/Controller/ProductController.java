package com.snapstore.SnapStore.Controller;

import com.snapstore.SnapStore.ExceptionHandler.ErrorResponse;
import com.snapstore.SnapStore.ExceptionHandler.ResourceNotFoundException;
import com.snapstore.SnapStore.Request.ProductRequest;
import com.snapstore.SnapStore.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/getproduct/{userId}")
    public ResponseEntity<?> getProduct(@PathVariable Integer userId) {
        try {
            List<ProductRequest> products = productService.getProduct(userId);
            if (products.isEmpty()) {
                throw new ResourceNotFoundException("No products found.");
            }
            return ResponseEntity.ok(products); 
        } catch (ResourceNotFoundException ex) {
            ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value(), "Resource Not Found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            ErrorResponse errorResponse = new ErrorResponse("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Error");
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getProductById/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Integer id) {
        try {
            ProductRequest products = productService.getProductById(id);
            if (products.getId() == null && products.getId() == 0) {
                throw new ResourceNotFoundException("No products found.");
            }
            return ResponseEntity.ok(products); 
        } catch (ResourceNotFoundException ex) {
            ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value(), "Resource Not Found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            ErrorResponse errorResponse = new ErrorResponse("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Error");
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestBody ProductRequest product) {
        try {
            if (product == null) {
                throw new IllegalArgumentException("Product data is invalid");
            }
            ProductRequest addedProduct = productService.addProduct(product);
            return ResponseEntity.status(HttpStatus.CREATED).body(addedProduct); 
        } catch (IllegalArgumentException ex) {
            ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value(), "Bad Request");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            ErrorResponse errorResponse = new ErrorResponse("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Error");
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
