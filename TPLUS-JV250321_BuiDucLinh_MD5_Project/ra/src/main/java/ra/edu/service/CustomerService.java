package ra.edu.service;

import org.springframework.data.domain.Page;
import ra.edu.model.entity.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> getAllActive();
    Customer save(Customer customer);
    Customer findById(Integer id);
    void softDelete(Integer id);
    Page<Customer> search(String keyword,Boolean isDeleted, int page, int size);
    boolean isEmailDuplicate(Customer customer);
    boolean isPhoneDuplicate(Customer customer);
}
