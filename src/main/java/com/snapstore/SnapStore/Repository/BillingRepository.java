package com.snapstore.SnapStore.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.snapstore.SnapStore.Enity.BillingEntity;

@Repository
public interface BillingRepository extends JpaRepository<BillingEntity, Integer> {

    @Query(value = "select b.billing_id,b.total_amount,b.total_qty,c.cust_name,c.email_id,c.mobile_no,b.created_date from billing b left join customer c on b.customer_id=c.cust_id WHERE b.created_by =?1 order by b.billing_id desc", nativeQuery = true)
    List<Object[]> getAllByUserId(Integer userId);

    @Query(value = "select b.billing_id, b.total_amount, b.total_qty, c.cust_name, c.email_id, c.mobile_no, b.total_gst, b.total_discount from billing b left join customer c on b.customer_id = c.cust_id WHERE b.billing_id = ?1", nativeQuery = true)
   List<Object[]> getBillingById(Integer id);

}
