package com.snapstore.SnapStore.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.snapstore.SnapStore.Request.ProductRequest;

@Service
public interface ProductService {

    public ProductRequest addProduct(ProductRequest prod);

    public List<ProductRequest> getProduct(Integer userId);

    public ProductRequest getProductById(Integer id);

}
