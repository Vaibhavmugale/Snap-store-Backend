package com.snapstore.SnapStore.Enity;

import jakarta.persistence.*;

@Entity
@Table(name = "customer")
public class CustomerEntity extends DateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cust_id")
    private Integer id;

    @Column(name = "cust_name", nullable = false, length = 255)
    private String customerName;

    @Column(name = "email_id", length = 100)
    private String emailId;

    @Column(name = "mobile_no", length = 14)
    private String mobileNo;

    @Column(name = "whatsapp_no", length = 14)
    private String whatsappNo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getWhatsappNo() {
        return whatsappNo;
    }

    public void setWhatsappNo(String whatsappNo) {
        this.whatsappNo = whatsappNo;
    }

    

}
