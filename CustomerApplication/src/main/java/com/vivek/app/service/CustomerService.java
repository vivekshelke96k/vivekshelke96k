package com.vivek.app.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestParam;

import com.vivek.app.dto.CustomerDto;
import com.vivek.app.entities.Customer;

public interface CustomerService {

	List<CustomerDto> getAllCustomers();
	CustomerDto saveCustomer(CustomerDto customerDto);
	CustomerDto getCustomerById(@RequestParam(value = "id") Integer id);
	CustomerDto updateCustomer(CustomerDto customerDto);
	void deleteCustomer(Integer id);
	List<CustomerDto> getCustomersByName(String searchTerm);
	List<CustomerDto> getCustomersByCity(String searchTerm);
	List<CustomerDto> getCustomersByEmail(String searchTerm);
	List<CustomerDto> getCustomersByPhone(long parseLong);
	
	Page<Customer> findByPageNumber(int pageNo,int pageSize,String sortBy,String sortDir);
}
