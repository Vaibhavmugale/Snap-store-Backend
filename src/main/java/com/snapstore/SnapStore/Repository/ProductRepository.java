package com.snapstore.SnapStore.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.snapstore.SnapStore.Enity.ProductEntity;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {

    @Query(value = "SELECT * FROM products WHERE created_by = ?1 order by product_id desc", nativeQuery = true)
    List<ProductEntity> getAllByUserId(Integer userId);

}
