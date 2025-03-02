package com.snapstore.SnapStore.Enity;

import jakarta.persistence.*;

@Entity
@Table(name = "user")
public class UserEntity extends DateEntity{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;
    
    @Column(name = "user_name", nullable = false)
    private String userName;
    
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "show_password", nullable = false)
    private String password2;
    
    @Column(name = "email_id", length = 100)
    private String emailId;
    
    @Column(name = "mobile_no", length = 14)
    private String mobileNo;
    
    @Column(name = "whatsapp_no", length = 14)
    private String whatsappNo;
    
    @Column(name = "company_name")
    private String companyName;

    @Column(name = "is_active")
    private Integer userStatus;

    @Column(name = "user_type")
    private Integer usertype;

    
    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
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

    public Integer getUsertype() {
        return usertype;
    }

    public void setUsertype(Integer usertype) {
        this.usertype = usertype;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }


}
