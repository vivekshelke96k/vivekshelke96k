package com.vivek.app.controller;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vivek.app.dto.CustomerDto;
import com.vivek.app.entities.Customer;
import com.vivek.app.service.CustomerService;

@Controller
@RequestMapping("/customers")
public class CustomerController {
	@Autowired
	CustomerService customerService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping("")
	public String getAllCustomers(Model model) {
		List<CustomerDto> customers = customerService.getAllCustomers();
		model.addAttribute("customers", customers);
		return "dashboard";
	}
	
	@GetMapping("/page/{pageNo}")
	public String findByPageNumber(@PathVariable int pageNo, Model model)
	{
		String sortBy="firstName";
		String sortDir="asc";
		int pageSize=6;
		Page<Customer> page=this.customerService.findByPageNumber(pageNo,pageSize,sortBy, sortDir);
		List<Customer> customers=page.getContent();
		List<CustomerDto> customerList = new ArrayList<CustomerDto>();
		for (Customer customer : customers) {
			CustomerDto customerDto;
			customerDto = this.modelMapper.map(customer, CustomerDto.class);
			customerList.add(customerDto);
		}
		model.addAttribute("currentPage",pageNo);
		model.addAttribute("totalPages",page.getTotalPages());
		model.addAttribute("totalItems",page.getTotalElements());
		
//		model.addAttribute("sortBy",sortBy);
//		model.addAttribute("sortDir",sortDir);
		model.addAttribute("customers",customerList);
		return "dashboard";
	}
	
	@PostMapping("")
	public String saveCustomer(@ModelAttribute("customer")CustomerDto customerDto, Model model) {
		CustomerDto response = this.customerService.saveCustomer(customerDto);
		model.addAttribute("customer", response);
		return "redirect:/customers";
	}
	
	@GetMapping("/update/{id}")
	public String updatCustomer(@PathVariable(value ="id") Integer id,Model model) {
		CustomerDto response=this.customerService.getCustomerById(id);
		model.addAttribute("customer", response);
		return "update";
	}
	@GetMapping("/update")
	public String update(@ModelAttribute("customer")CustomerDto customerDto, Model model) {
		CustomerDto response = this.customerService.saveCustomer(customerDto);
		model.addAttribute("customer", response);
		return "redirect:/customers";
	}
	
	@GetMapping("/id")
	public String getCustomerById(@RequestParam(value = "id") Integer id, Model model) {
		CustomerDto response=this.customerService.getCustomerById(id);
		model.addAttribute("customers",response);
		return "dashboard";
		
	}
	@GetMapping("/delete/{id}")
	public String deleteCustomer(@PathVariable(value="id") Integer id)
	{
		this.customerService.deleteCustomer(id);
		return "redirect:/customers";
	}
	
	
	@GetMapping("/register")
	public String getRegistrationPage(Model model) {
		model.addAttribute("customer",new CustomerDto());
		return "register";
	}
	

	@PostMapping("/filter")
	public String filterCustomers(@RequestParam String filterBy, @RequestParam String searchTerm, Model model) {
	    List<CustomerDto> filteredCustomers = new ArrayList<>();

	    try {
	        if ("NAME".equals(filterBy)) {
	            filteredCustomers = customerService.getCustomersByName(searchTerm);
	        } else if ("CITY".equals(filterBy)) {
	            filteredCustomers = customerService.getCustomersByCity(searchTerm);
	        } else if ("EMAIL".equals(filterBy)) {
	            filteredCustomers = customerService.getCustomersByEmail(searchTerm);
	        } else if ("PHONE".equals(filterBy)) {
	            filteredCustomers = customerService.getCustomersByPhone(Long.parseLong(searchTerm));
	        }
	    } catch (NumberFormatException e) {
	        return "redirect:/customers?error=invalidPhone";
	    }

	    model.addAttribute("customers", filteredCustomers);
	    return "dashboard";
	}
	
	
}
