package com.snapstore.SnapStore.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.snapstore.SnapStore.Enity.ProductEntity;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {

    @Query(value = "SELECT * FROM products WHERE created_by = ?1 order by product_id desc", nativeQuery = true)
    List<ProductEntity> getAllByUserId(Integer userId);

    @Query(value = "SELECT COUNT(DISTINCT p.product_id) AS product_count,COUNT(DISTINCT c.cust_id) AS customer_count,COUNT(DISTINCT b.billing_id) AS billing_count FROM products p LEFT JOIN customer c ON c.created_by = p.created_by AND c.created_by =?1 LEFT JOIN billing b ON b.created_by = p.created_by AND b.created_by =?1 WHERE p.created_by =?1", nativeQuery = true)
    List<Object[]> getdashboard(Integer id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE products SET remaining_qty = remaining_qty - ?1 WHERE product_id = ?2", nativeQuery = true)
    void updateProductQty(int quantity, Integer id);

}
