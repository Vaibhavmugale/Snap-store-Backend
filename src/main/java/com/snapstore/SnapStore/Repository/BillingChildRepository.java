package com.snapstore.SnapStore.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.snapstore.SnapStore.Enity.BillingChildEntity;

@Repository
public interface BillingChildRepository extends JpaRepository<BillingChildEntity, Integer> {

    @Query(value = "SELECT * FROM billing WHERE created_by = ?1 order by cust_id desc", nativeQuery = true)
    List<BillingChildEntity> getAllByUserId(Integer userId);
}
