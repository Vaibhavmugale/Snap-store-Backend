package com.snapstore.SnapStore.Enity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "billing_child")
public class BillingChildEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "billing_child_id")
    private Integer id;

    @Column(name = "billing_id")
    private Integer billing;

    @Column(name = "product_id")
    private Integer product;

    @Column(name = "product_price", precision = 10, scale = 2)
    private BigDecimal productPrice;

    @Column(name = "qty", precision = 10, scale = 2)
    private Integer qty;

    @Column(name = "total", precision = 10, scale = 2)
    private BigDecimal total;

    public Integer getBilling() {
        return billing;
    }

    public void setBilling(Integer billing) {
        this.billing = billing;
    }

    public Integer getProduct() {
        return product;
    }

    public void setProduct(Integer product) {
        this.product = product;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
