package ra.edu.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ra.edu.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {
    List<Product> findByIsDeletedFalse();
    @Query("SELECT p FROM Product p " +
            "WHERE (:keyword IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "AND (:brand IS NULL OR LOWER(p.brand) LIKE LOWER(CONCAT('%', :brand, '%'))) " +
            "AND ( " +
            "   (:minStock IS NULL AND :maxStock IS NULL) " +
            "   OR (:minStock IS NOT NULL AND :maxStock IS NULL AND p.stock >= :minStock) " +
            "   OR (:minStock IS NULL AND :maxStock IS NOT NULL AND p.stock <= :maxStock) " +
            "   OR (:minStock IS NOT NULL AND :maxStock IS NOT NULL AND p.stock BETWEEN :minStock AND :maxStock) " +
            ") " +
            "AND (:isDeleted IS NULL OR :isDeleted = p.isDeleted)")
    Page<Product> search(
            @Param("keyword") String keyword,
            @Param("brand") String brand,
            @Param("minStock") Integer minStock,
            @Param("maxStock") Integer maxStock,
            @Param("isDeleted") Boolean isDeleted,
            Pageable pageable
    );
}
