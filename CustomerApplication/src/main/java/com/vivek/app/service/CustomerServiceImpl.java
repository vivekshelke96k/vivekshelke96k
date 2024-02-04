package com.vivek.app.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.vivek.app.dto.CustomerDto;
import com.vivek.app.entities.Customer;
import com.vivek.app.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public List<CustomerDto> getAllCustomers() {
		List<Customer> customers = customerRepository.findAll();	
		List<CustomerDto> customerList = new ArrayList<CustomerDto>();
		for (Customer customer : customers) {
			CustomerDto customerDto;
			customerDto = this.modelMapper.map(customer, CustomerDto.class);
			customerList.add(customerDto);
		}
		/*customers
		.stream()
		.map(customer -> this.modelMapper.map(customer, CustomerDto.class))
		.collect(Collectors.toList());*/
		return customerList;
	}

	@Override
	public CustomerDto saveCustomer(CustomerDto customerDto) {
		Customer customer = this.modelMapper.map(customerDto, Customer.class);
		Customer response = customerRepository.save(customer);
		CustomerDto responseDto = this.modelMapper.map(response, CustomerDto.class);
		return responseDto;
	}

	@Override
	public CustomerDto updateCustomer(CustomerDto customerDto) {
		Customer customer = this.modelMapper.map(customerDto, Customer.class);
		Customer response = customerRepository.save(customer);
		CustomerDto responseDto = this.modelMapper.map(response, CustomerDto.class);
		return responseDto;
	}

	@Override
	public CustomerDto getCustomerById(Integer id) {
		Customer customer=customerRepository.findById(id).get();
		CustomerDto responseDto=this.modelMapper.map(customer, CustomerDto.class);
		return  responseDto;
	}

	@Override
	public List<CustomerDto> getCustomersByName(String searchTerm) {
		List<Customer> customers=customerRepository.findByFirstName(searchTerm);
		List<CustomerDto> customerList = new ArrayList<CustomerDto>();
		for (Customer customer : customers) {
			CustomerDto customerDto;
			customerDto = this.modelMapper.map(customer, CustomerDto.class);
			customerList.add(customerDto);
		}
		return customerList;
	}

	@Override
	public List<CustomerDto> getCustomersByCity(String searchTerm) {
		List<Customer> customers=customerRepository.findByCity(searchTerm);
		List<CustomerDto> customerList = new ArrayList<CustomerDto>();
		for (Customer customer : customers) {
			CustomerDto customerDto;
			customerDto = this.modelMapper.map(customer, CustomerDto.class);
			customerList.add(customerDto);
		}
		return customerList;
	}

	@Override
	public List<CustomerDto> getCustomersByEmail(String searchTerm) {
		List<Customer> customers=customerRepository.findByEmail(searchTerm);
		List<CustomerDto> customerList = new ArrayList<CustomerDto>();
		for (Customer customer : customers) {
			CustomerDto customerDto;
			customerDto = this.modelMapper.map(customer, CustomerDto.class);
			customerList.add(customerDto);
		}
		return customerList;
	}

	@Override
	public List<CustomerDto> getCustomersByPhone(long searchTerm) {
		List<Customer> customers=customerRepository.findByPhone(searchTerm);
		List<CustomerDto> customerList = new ArrayList<CustomerDto>();
		for (Customer customer : customers) {
			CustomerDto customerDto;
			customerDto = this.modelMapper.map(customer, CustomerDto.class);
			customerList.add(customerDto);
		}
		return customerList;
	}

	@Override
	public void deleteCustomer(Integer id) {
		customerRepository.deleteById(id);
	}

	@Override
	public Page<Customer> findByPageNumber(int pageNo,int pageSize, String sortBy, String sortDir) {
		Sort sort=sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		Pageable pageable=PageRequest.of((pageNo-1), pageSize,sort);
		Page<Customer> pageCustomer=this.customerRepository.findAll(pageable);
		return pageCustomer;
	}
}

