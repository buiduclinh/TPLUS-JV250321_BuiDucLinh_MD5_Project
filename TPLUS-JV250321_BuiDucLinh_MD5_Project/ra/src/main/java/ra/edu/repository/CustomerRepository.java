package ra.edu.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ra.edu.model.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    List<Customer> findByIsDeletedFalse();

    Page<Customer> findByNameContainingIgnoreCase(String keyword, PageRequest of);

    boolean existsByPhone(String phone);

    boolean existsByEmail(String email);

    @Query("SELECT c FROM Customer c " +
            "WHERE :isDeleted IS NULL OR c.isDeleted = :isDeleted " +
            "AND (:keyword IS NULL OR " +
            "LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.phone) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.email) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.address) LIKE LOWER(CONCAT('%', :keyword, '%'))) "
    )
    Page<Customer> search(
            @Param("keyword") String keyword,
            @Param("isDeleted") Boolean isDeleted,
            Pageable pageable
    );

    Customer findByEmail(String email);

    Customer findByPhone(String phone);
}
