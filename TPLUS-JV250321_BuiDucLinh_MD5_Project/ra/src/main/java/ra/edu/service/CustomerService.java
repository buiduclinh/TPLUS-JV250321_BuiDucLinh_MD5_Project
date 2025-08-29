package ra.edu.service;

import ra.edu.model.entity.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> getAllActive();
    Customer save(Customer customer);
    Customer findById(Integer id);
    void softDelete(Integer id);
}
