package com.snapstore.SnapStore.Request;

public class CustomerRequest extends DateRequest{

    private Integer id;
    private String customerName;
    private String emailId;
    private String mobileNo;
    private String whatsappNo;
    private Integer isEdited;
    private Integer UserId;

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
    public Integer getIsEdited() {
        return isEdited;
    }
    public void setIsEdited(Integer isEdited) {
        this.isEdited = isEdited;
    }
    public Integer getUserId() {
        return UserId;
    }
    public void setUserId(Integer userId) {
        UserId = userId;
    }

    
    
}
