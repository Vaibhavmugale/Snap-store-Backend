package com.snapstore.SnapStore.serviceImpl;

import java.math.BigDecimal;
import java.util.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapstore.SnapStore.Enity.BillingChildEntity;
import com.snapstore.SnapStore.Enity.BillingEntity;
import com.snapstore.SnapStore.Repository.BillingChildRepository;
import com.snapstore.SnapStore.Repository.BillingRepository;
import com.snapstore.SnapStore.Repository.ProductRepository;
import com.snapstore.SnapStore.Request.BillingRequest;
import com.snapstore.SnapStore.Request.ProductRequest;
import com.snapstore.SnapStore.Request.ReportRequest;
import com.snapstore.SnapStore.service.BillingService;

@Service
public class BillingServiceImpl implements BillingService {

    @Autowired
    private BillingRepository billingRepo;

    @Autowired
    private BillingChildRepository billingChildRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    ProductRepository productRepo;

    @Override
    public List<BillingRequest> getBilling(Integer billingId) {
        List<Object[]> results = billingRepo.getAllByUserId(billingId);
        List<BillingRequest> requests = new ArrayList<>();

        for (Object[] row : results) {
            BillingRequest request = new BillingRequest();
            request.setId(row[0] != null ? (Integer) row[0] : 0);
            request.setTotalAmount(row[1] != null ? (BigDecimal) row[1] : BigDecimal.ZERO);
            request.setTotalQty(row[2] != null ? (BigDecimal) row[2] : BigDecimal.ZERO);
            request.setCustomerName(row[3] != null ? (String) row[3] : "");
            request.setCustomerEmail(row[4] != null ? (String) row[4] : "");
            request.setCustomerPhone(row[5] != null ? (String) row[5] : "");
            request.setCreatedDate(row[6] != null ? (Date) row[6] : null);
            requests.add(request);
        }

        return requests;
    }

    @Override
    public BillingRequest getBillingById(Integer id) {
        List<Object[]> results = billingRepo.getBillingById(id);

        for (Object[] row : results) {
            BillingRequest request = new BillingRequest();
            request.setId(row[0] != null ? (Integer) row[0] : 0);
            request.setTotalAmount(row[1] != null ? (BigDecimal) row[1] : BigDecimal.ZERO);
            request.setTotalQty(row[2] != null ? (BigDecimal) row[2] : BigDecimal.ZERO);
            request.setCustomerName(row[3] != null ? (String) row[3] : "");
            request.setCustomerEmail(row[4] != null ? (String) row[4] : "");
            request.setCustomerPhone(row[5] != null ? (String) row[5] : "");
            request.setTotalGst(row[6] != null ? (BigDecimal) row[6] : BigDecimal.ZERO);
            request.setTotalDisc(row[7] != null ? (BigDecimal) row[7] : BigDecimal.ZERO);

            List<Object[]> result = billingChildRepo.getBillingChildById(id);
            List<ProductRequest> requests = new ArrayList<>();
            for (Object[] child : result) {
                ProductRequest childReq = new ProductRequest();
                childReq.setProductName(child[0] != null ? (String) child[0] : "");
                childReq.setPrice(child[1] != null ? (BigDecimal) child[1] : BigDecimal.ZERO);
                childReq.setGst(child[2] != null ? (BigDecimal) child[2] : BigDecimal.ZERO);
                childReq.setDiscount(child[3] != null ? (BigDecimal) child[3] : BigDecimal.ZERO);
                childReq.setTotalQty(child[4] != null ? (Integer) child[4] : 0);
                childReq.setQuantity(child[5] != null ? (Integer) child[5] : 0);
                childReq.setTotal(child[6] != null ? (BigDecimal) child[6] : BigDecimal.ZERO);
                requests.add(childReq);
            }
            request.setSelectedProducts(requests);
            return request;
        }

        return null;
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
        billing.getSelectedProducts().stream().forEach(e -> {
            BillingChildEntity child = new BillingChildEntity();
            child.setBilling(entites.getId());
            child.setProduct(e.getId());
            child.setProductPrice(e.getPrice());
            child.setQty(e.getQuantity());
            child.setTotal(e.getTotal());

            billingChildRepo.save(child);
            productRepo.updateProductQty(e.getQuantity(), e.getId());
        });
        return modelMapper.map(entity, BillingRequest.class);
    }
@Override
public List<ProductRequest> generateReport(ReportRequest report) {
    if (report == null) {
        throw new IllegalArgumentException("Report data is missing.");
    }

    Integer customerId = report.getCustomerId();
    Integer productId = report.getProductId();
    LocalDate fromDate = report.getFromDate();
    LocalDate toDate = report.getToDate();

    boolean hasCustomer = customerId != null && customerId != 0;
    boolean hasProduct = productId != null && productId != 0;
    boolean hasFrom = fromDate != null;
    boolean hasTo = toDate != null;

    List<ProductRequest> requests = new ArrayList<>();
    List<Object[]> results = null;

    if ((hasFrom && !hasTo) || (!hasFrom && hasTo)) {
        throw new IllegalArgumentException("Both From Date and To Date are required together.");
    }

    if (!hasCustomer && !hasProduct && !hasFrom && !hasTo) {
        throw new IllegalArgumentException("At least one filter must be selected.");
    }

    if ((hasCustomer && hasProduct && (!hasFrom || !hasTo)) ||
        (hasCustomer && hasFrom && !hasTo) ||
        (hasProduct && hasFrom && !hasTo) ||
        (hasCustomer && hasTo && !hasFrom) ||
        (hasProduct && hasTo && !hasFrom)) {
        throw new IllegalArgumentException("Date range must be complete if used with other filters.");
    }

    if (hasCustomer && hasProduct && hasFrom && hasTo) {
        results = billingRepo.getReportByAllFilters(customerId, productId, fromDate, toDate);
    } else if (hasCustomer && hasProduct) {
        results = billingRepo.getReportByCustomerAndProduct(customerId, productId);
    } else if (hasCustomer && hasFrom && hasTo) {
        results = billingRepo.getReportByCustomerAndDate(customerId, fromDate, toDate);
    } else if (hasProduct && hasFrom && hasTo) {
        results = billingRepo.getReportByProductAndDate(productId, fromDate, toDate);
    } else if (hasCustomer) {
        results = billingRepo.getReportByCustomer(customerId);
    } else if (hasProduct) {
        results = billingRepo.getReportByProduct(productId);
    } else if (hasFrom && hasTo) {
        results = billingRepo.getReportByDateRange(fromDate, toDate);
    } else {
        throw new IllegalArgumentException("Invalid filter combination.");
    }

    for (Object[] row : results) {
        ProductRequest request = new ProductRequest();
        request.setProductName(row[0] != null ? (String) row[0] : "");
        request.setDiscount(row[1] != null ? (BigDecimal) row[1] : BigDecimal.ZERO);
        request.setGst(row[2] != null ? (BigDecimal) row[2] : BigDecimal.ZERO);
        request.setPrice(row[3] != null ? (BigDecimal) row[3] : BigDecimal.ZERO);
        request.setTotalQty(row[4] != null ? (Integer) row[4] : 0);
        request.setTotal(row[5] != null ? (BigDecimal) row[5] : BigDecimal.ZERO);
        request.setCustomerName(row[6] != null ? (String) row[6] : "");
        request.setCustomerEmail(row[7] != null ? (String) row[7] : "");
        request.setCustomerPhone(row[8] != null ? (String) row[8] : "");
        request.setCreatedDate(row[9] != null ? (Date) row[9] : null);
        requests.add(request);
    }

    return requests;
}
}