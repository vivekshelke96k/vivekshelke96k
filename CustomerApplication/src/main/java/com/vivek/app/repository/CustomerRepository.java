package com.vivek.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vivek.app.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	List<Customer> findByFirstName(String firstName);
	List<Customer> findByCity(String city);
	List<Customer> findByEmail(String email);
	List<Customer> findByPhone(Long phone);
}
