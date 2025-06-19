package com.snapstore.SnapStore.Enity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

import com.snapstore.SnapStore.Request.ProductRequest;

@Entity
@Table(name = "billing")
public class BillingEntity extends DateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "billing_id")
    private Integer id;

    @Column(name = "total_amount", precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "total_gst", precision = 10, scale = 2)
    private BigDecimal totalGst;

    @Column(name = "total_discount", precision = 10, scale = 2)
    private BigDecimal totalDisc;

    @Column(name = "total_qty", precision = 10, scale = 2)
    private BigDecimal totalQty;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "customer_id")
    private Integer customerId;

    @Column(name = "company_id")
    private Integer companyId;

    @Transient
    private List<ProductRequest> selectedProducts;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getTotalGst() {
        return totalGst;
    }

    public void setTotalGst(BigDecimal totalGst) {
        this.totalGst = totalGst;
    }

    public BigDecimal getTotalDisc() {
        return totalDisc;
    }

    public void setTotalDisc(BigDecimal totalDisc) {
        this.totalDisc = totalDisc;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public BigDecimal getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(BigDecimal totalQty) {
        this.totalQty = totalQty;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public List<ProductRequest> getSelectedProducts() {
        return selectedProducts;
    }

    public void setSelectedProducts(List<ProductRequest> selectedProducts) {
        this.selectedProducts = selectedProducts;
    }
    
}
