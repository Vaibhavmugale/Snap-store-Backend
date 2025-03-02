package com.snapstore.SnapStore.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.snapstore.SnapStore.Request.CustomerRequest;

@Service
public interface CustomerService {

    public List<CustomerRequest> getCustomer(Integer userId);

    public CustomerRequest getCustomerById(Integer id);

    public CustomerRequest addCustomer(CustomerRequest customer);

}
