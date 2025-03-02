package com.snapstore.SnapStore.serviceImpl;
import java.sql.Timestamp;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapstore.SnapStore.Enity.CustomerEntity;
import com.snapstore.SnapStore.Repository.CustomerRepository;
import com.snapstore.SnapStore.Request.CustomerRequest;
import com.snapstore.SnapStore.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public List<CustomerRequest> getCustomer(Integer customerId) {
        List<CustomerEntity> entities = customerRepo.getAllByUserId(customerId);
        return modelMapper.map(entities, new TypeToken<List<CustomerRequest>>(){}.getType());  
    }

    @Override
    public CustomerRequest getCustomerById(Integer id) {
        CustomerEntity entities = customerRepo.findById(id)
        .orElseThrow(() -> new RuntimeException("Product not found with ID: " + id));
        return modelMapper.map(entities, CustomerRequest.class);
    }

    @Override
    public CustomerRequest addCustomer(CustomerRequest customer) {
        
        CustomerEntity entity = modelMapper.map(customer, CustomerEntity.class);
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
    
        if (customer.getId() != null && customer.getId() > 0) {
            entity.setModifiedDate(currentTime);
            entity.setModifiedBy(customer.getUserId());
        } else {
            entity.setCreatedDate(currentTime);
            entity.setModifiedDate(currentTime);
            entity.setCreatedBy(customer.getUserId()); 
            entity.setModifiedBy(customer.getUserId());
        }
    
        entity = customerRepo.save(entity);
        return modelMapper.map(entity, CustomerRequest.class);
    }

}
