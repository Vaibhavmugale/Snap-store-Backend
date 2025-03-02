package com.snapstore.SnapStore.Enity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class DateEntity {

       @Column(name = "created_by", nullable = false)
    private Integer createdBy;
    
    @Column(name = "modified_by")
    private Integer modifiedBy;
    
    @Column(name = "created_date", nullable = false)
    private Date createdDate;
    
    @Column(name = "modified_date")
    private Date modifiedDate;

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Integer modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    
}
