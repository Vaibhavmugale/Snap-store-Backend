package com.snapstore.SnapStore.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.snapstore.SnapStore.Enity.BillingChildEntity;

@Repository
public interface BillingChildRepository extends JpaRepository<BillingChildEntity, Integer> {

    @Query(value = "select p.name,b.product_price,p.gst,p.discount,p.total_qty,b.qty,b.total from billing_child b left join products p on b.product_id=p.product_id WHERE b.billing_id =?1 order by b.billing_id desc", nativeQuery = true)
    List<Object[]> getBillingChildById(Integer id);

}
