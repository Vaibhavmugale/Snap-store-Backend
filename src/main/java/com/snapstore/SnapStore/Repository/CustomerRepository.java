package com.snapstore.SnapStore.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.snapstore.SnapStore.Enity.CustomerEntity;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Integer> {

    @Query(value = "SELECT * FROM customer WHERE created_by = ?1 order by cust_id desc", nativeQuery = true)
    List<CustomerEntity> getAllByUserId(Integer userId);
}
