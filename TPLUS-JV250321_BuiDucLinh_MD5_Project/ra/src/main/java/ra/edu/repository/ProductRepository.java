package ra.edu.repository;

import ra.edu.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {
    List<Product> findByIsDeletedFalse();
    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);
    Page<Product> findByStockBetween(Integer minStock, Integer maxStock, Pageable pageable);
}
