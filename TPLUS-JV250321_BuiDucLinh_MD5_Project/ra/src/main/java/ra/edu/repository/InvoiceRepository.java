package ra.edu.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ra.edu.model.entity.Invoice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.edu.model.entity.InvoiceStatus;

import java.util.List;


@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {

    @Query("SELECT DISTINCT i FROM Invoice i " +
            "LEFT JOIN i.customer c " +
            "LEFT JOIN i.invoiceDetails d " +
            "LEFT JOIN d.product p " +
            "WHERE (:keyword IS NULL OR " +
            "      CAST(i.id AS string) LIKE CONCAT('%', :keyword, '%') OR " +
            "      LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "      LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "AND (:status IS NULL OR i.status = :status) " +
            "AND (:day IS NULL OR FUNCTION('DAY', i.createdAt) = :day) " +
            "AND (:month IS NULL OR FUNCTION('MONTH', i.createdAt) = :month) " +
            "AND (:year IS NULL OR FUNCTION('YEAR', i.createdAt) = :year)")
    Page<Invoice> search(@Param("keyword") String keyword,
                         @Param("status") InvoiceStatus status,
                         @Param("day") Integer day,
                         @Param("month") Integer month,
                         @Param("year") Integer year,
                         Pageable pageable);


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


    @Query("SELECT DISTINCT YEAR(i.createdAt) FROM Invoice i ORDER BY YEAR(i.createdAt) DESC")
    List<Integer> findAllYears();
}