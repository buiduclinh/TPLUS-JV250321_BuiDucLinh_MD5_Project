package ra.edu.service.impl;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ra.edu.model.entity.Customer;
import ra.edu.repository.CustomerRepository;
import ra.edu.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<Customer> getAllActive() {
        return customerRepository.findByIsDeletedFalse();
    }

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer findById(Integer id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    @Override
    public void softDelete(Integer id) {
        Customer customer = findById(id);
        customer.setIsDeleted(true);
        customerRepository.save(customer);
    }

    @Override
    public Page<Customer> search(String keyword, Boolean isDeleted, int page, int size) {
        if (keyword == null || keyword.trim().isEmpty()) {
            keyword = null;
        }
        Pageable pageable = PageRequest.of(page, size);
        return customerRepository.search(keyword, isDeleted, pageable);
    }

    @Override
    public boolean isEmailDuplicate(Customer customer) {
        Customer existing = customerRepository.findByEmail(customer.getEmail());
        if (existing == null) return false;

        return !existing.getId().equals(customer.getId());
    }

    @Override
    public boolean isPhoneDuplicate(Customer customer) {
        Customer existing = customerRepository.findByPhone(customer.getPhone());
        if (existing == null) return false;

        return !existing.getId().equals(customer.getId());
    }
}
