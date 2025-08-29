package ra.edu.service.impl;


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
}
