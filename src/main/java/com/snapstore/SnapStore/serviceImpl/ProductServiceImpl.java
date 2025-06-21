package com.snapstore.SnapStore.serviceImpl;

import java.sql.Timestamp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import com.snapstore.SnapStore.Enity.ProductEntity;
import com.snapstore.SnapStore.Repository.ProductRepository;
import com.snapstore.SnapStore.Request.ProductRequest;
import com.snapstore.SnapStore.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProductRequest addProduct(ProductRequest prod) {
        ProductEntity entity = modelMapper.map(prod, ProductEntity.class);
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
    
        if (prod.getId() != null && prod.getId() > 0) {
            entity.setModifiedDate(currentTime);
            entity.setModifiedBy(prod.getUserId());
        } else {
            entity.setCreatedDate(currentTime);
            entity.setModifiedDate(currentTime);
            
            entity.setCreatedBy(prod.getUserId()); 
            entity.setModifiedBy(prod.getUserId());
        }
    
        entity = productRepo.save(entity);
        return modelMapper.map(entity, ProductRequest.class);
    }
    

    @Override
    public List<ProductRequest> getProduct(Integer userId) {
        List<ProductEntity> entities = productRepo.getAllByUserId(userId);
        return modelMapper.map(entities, new TypeToken<List<ProductRequest>>(){}.getType());  
    }

    @Override
    public ProductRequest getProductById(Integer id) {
        ProductEntity entity = productRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Product not found with ID: " + id));
        return modelMapper.map(entity, ProductRequest.class);
    }

     @Override
    public ProductRequest getdashboard(Integer id) {

        List<Object[]> results = productRepo.getdashboard(id);

        for (Object[] row : results) {
            ProductRequest request = new ProductRequest();
            request.setTotalProducts(row[0] != null ? (Long) row[0] : 0);
            request.setTotalCustomer(row[1] != null ? (Long) row[1] : 0);
            request.setTotalBills(row[2] != null ? (Long) row[2] : 0);
            return request;
        }
        return null;
    }
    
}
