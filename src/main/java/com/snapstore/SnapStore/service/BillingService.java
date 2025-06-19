package com.snapstore.SnapStore.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.snapstore.SnapStore.Request.BillingRequest;

@Service
public interface BillingService {

    public List<BillingRequest> getBilling(Integer userId);

    public BillingRequest getBillingById(Integer id);

    public BillingRequest addBilling(BillingRequest billing);

}
