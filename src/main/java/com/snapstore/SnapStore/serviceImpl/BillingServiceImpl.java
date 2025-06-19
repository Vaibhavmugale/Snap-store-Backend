package com.snapstore.SnapStore.serviceImpl;
import java.sql.Timestamp;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapstore.SnapStore.Enity.BillingChildEntity;
import com.snapstore.SnapStore.Enity.BillingEntity;
import com.snapstore.SnapStore.Repository.BillingChildRepository;
import com.snapstore.SnapStore.Repository.BillingRepository;
import com.snapstore.SnapStore.Request.BillingRequest;
import com.snapstore.SnapStore.service.BillingService;

@Service
public class BillingServiceImpl implements BillingService{

    @Autowired
    private BillingRepository billingRepo;

     @Autowired
    private BillingChildRepository billingChildRepo;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public List<BillingRequest> getBilling(Integer billingId) {
        List<BillingEntity> entities = billingRepo.getAllByUserId(billingId);
        return modelMapper.map(entities, new TypeToken<List<BillingRequest>>(){}.getType());  
    }

    @Override
    public BillingRequest getBillingById(Integer id) {
        BillingEntity entities = billingRepo.findById(id)
        .orElseThrow(() -> new RuntimeException("Product not found with ID: " + id));
        return modelMapper.map(entities, BillingRequest.class);
    }

    @Override
    public BillingRequest addBilling(BillingRequest billing) {
        
        BillingEntity entity = modelMapper.map(billing, BillingEntity.class);
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
    
        if (billing.getId() != null && billing.getId() > 0) {
            entity.setModifiedDate(currentTime);
            entity.setModifiedBy(billing.getUserId());
        } else {
            entity.setCreatedDate(currentTime);
            entity.setModifiedDate(currentTime);
            entity.setCreatedBy(billing.getUserId()); 
            entity.setModifiedBy(billing.getUserId());
        }
    
        BillingEntity entites = billingRepo.save(entity);
        billing.getSelectedProducts().stream().forEach(e->{
            BillingChildEntity child=new BillingChildEntity();
            child.setBilling(entites.getId());
            child.setProduct(e.getId());
            child.setProductPrice(e.getPrice());
            child.setQty(e.getQuantity());

            billingChildRepo.save(child);

        });
        return modelMapper.map(entity, BillingRequest.class);
    }

}
