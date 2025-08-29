package ra.edu.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ra.edu.model.entity.Invoice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface InvoiceRepository extends JpaRepository<Invoice,Integer> {

    @Query("SELECT DISTINCT i FROM Invoice i " +
            "LEFT JOIN i.customer c " +
            "LEFT JOIN i.invoiceDetails d " +
            "LEFT JOIN d.product p " +
            "WHERE CAST(i.id AS string) LIKE %:keyword% " +
            "   OR CAST(c.id AS string) LIKE %:keyword% " +
            "   OR LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "   OR CAST(p.id AS string) LIKE %:keyword% " +
            "   OR LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Invoice> search(@Param("keyword") String keyword);

    @Query("SELECT DATE(i.createdAt), SUM(i.totalAmount) " +
            "FROM Invoice i " +
            "GROUP BY DATE(i.createdAt) " +
            "ORDER BY DATE(i.createdAt)")
    List<Object[]> getRevenueByDay();

    @Query("SELECT MONTH(i.createdAt), YEAR(i.createdAt), SUM(i.totalAmount) " +
            "FROM Invoice i " +
            "GROUP BY YEAR(i.createdAt), MONTH(i.createdAt) " +
            "ORDER BY YEAR(i.createdAt), MONTH(i.createdAt)")
    List<Object[]> getRevenueByMonth();

    @Query("SELECT YEAR(i.createdAt), SUM(i.totalAmount) " +
            "FROM Invoice i " +
            "GROUP BY YEAR(i.createdAt) " +
            "ORDER BY YEAR(i.createdAt)")
    List<Object[]> getRevenueByYear();
}