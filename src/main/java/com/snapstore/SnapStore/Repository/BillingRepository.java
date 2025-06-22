package com.snapstore.SnapStore.Repository;

import java.time.LocalDate;
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

    @Query(value = "SELECT p.name, p.discount, p.gst, bc.product_price, bc.qty, bc.total, c.cust_name, c.email_id, c.mobile_no, b.created_date FROM billing b LEFT JOIN billing_child bc ON b.billing_id = bc.billing_id LEFT JOIN products p ON p.product_id = bc.product_id LEFT JOIN customer c ON c.cust_id = b.customer_id WHERE b.customer_id = ?1 AND bc.product_id = ?2 AND DATE_FORMAT(b.created_date, '%Y-%m-%d') >= ?3 AND DATE_FORMAT(b.created_date, '%Y-%m-%d') <= ?4", nativeQuery = true)
    List<Object[]> getReportByAllFilters(Integer customerId, Integer productId, LocalDate fromDate, LocalDate toDate);

    @Query(value = "SELECT p.name, p.discount, p.gst, bc.product_price, bc.qty, bc.total, c.cust_name, c.email_id, c.mobile_no, b.created_date FROM billing b LEFT JOIN billing_child bc ON b.billing_id = bc.billing_id LEFT JOIN products p ON p.product_id = bc.product_id LEFT JOIN customer c ON c.cust_id = b.customer_id WHERE b.customer_id = ?1 AND bc.product_id = ?2", nativeQuery = true)
    List<Object[]> getReportByCustomerAndProduct(Integer customerId, Integer productId);

    @Query(value = "SELECT p.name, p.discount, p.gst, bc.product_price, bc.qty, bc.total, c.cust_name, c.email_id, c.mobile_no, b.created_date FROM billing b LEFT JOIN billing_child bc ON b.billing_id = bc.billing_id LEFT JOIN products p ON p.product_id = bc.product_id LEFT JOIN customer c ON c.cust_id = b.customer_id WHERE b.customer_id = ?1 AND DATE_FORMAT(b.created_date, '%Y-%m-%d') >= ?2 AND DATE_FORMAT(b.created_date, '%Y-%m-%d') <= ?3", nativeQuery = true)
    List<Object[]> getReportByCustomerAndDate(Integer customerId, LocalDate fromDate, LocalDate toDate);

    @Query(value = "SELECT p.name, p.discount, p.gst, bc.product_price, bc.qty, bc.total, c.cust_name, c.email_id, c.mobile_no, b.created_date FROM billing b LEFT JOIN billing_child bc ON b.billing_id = bc.billing_id LEFT JOIN products p ON p.product_id = bc.product_id LEFT JOIN customer c ON c.cust_id = b.customer_id WHERE bc.product_id = ?1 AND DATE_FORMAT(b.created_date, '%Y-%m-%d') >= ?2 AND DATE_FORMAT(b.created_date, '%Y-%m-%d') <= ?3", nativeQuery = true)
    List<Object[]> getReportByProductAndDate(Integer productId, LocalDate fromDate, LocalDate toDate);

    @Query(value = "SELECT p.name, p.discount, p.gst, bc.product_price, bc.qty, bc.total, c.cust_name, c.email_id, c.mobile_no, b.created_date FROM billing b LEFT JOIN billing_child bc ON b.billing_id = bc.billing_id LEFT JOIN products p ON p.product_id = bc.product_id LEFT JOIN customer c ON c.cust_id = b.customer_id WHERE b.customer_id = ?1", nativeQuery = true)
    List<Object[]> getReportByCustomer(Integer customerId);

    @Query(value = "SELECT p.name, p.discount, p.gst, bc.product_price, bc.qty, bc.total, c.cust_name, c.email_id, c.mobile_no, b.created_date FROM billing b LEFT JOIN billing_child bc ON b.billing_id = bc.billing_id LEFT JOIN products p ON p.product_id = bc.product_id LEFT JOIN customer c ON c.cust_id = b.customer_id WHERE bc.product_id = ?1", nativeQuery = true)
    List<Object[]> getReportByProduct(Integer productId);

    @Query(value = "SELECT p.name, p.discount, p.gst, bc.product_price, bc.qty, bc.total, c.cust_name, c.email_id, c.mobile_no, b.created_date FROM billing b LEFT JOIN billing_child bc ON b.billing_id = bc.billing_id LEFT JOIN products p ON p.product_id = bc.product_id LEFT JOIN customer c ON c.cust_id = b.customer_id WHERE DATE_FORMAT(b.created_date, '%Y-%m-%d') >= ?1 AND DATE_FORMAT(b.created_date, '%Y-%m-%d') <= ?2", nativeQuery = true)
    List<Object[]> getReportByDateRange(LocalDate fromDate, LocalDate toDate);
}

