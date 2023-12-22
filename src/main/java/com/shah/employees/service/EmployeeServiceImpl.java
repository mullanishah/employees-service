package com.shah.employees.service;

import java.util.List;
import java.util.Optional;
import com.shah.employees.exception.ResourceNotFoundException;
import com.shah.employees.model.Employee;
import com.shah.employees.repository.EmployeeRepository;

/**
 * @author Shahrukh
 *
 */
public class EmployeeServiceImpl implements EmployeeService {
	
	//@Autowired - not required, as ctor based dependency injections is implemented
	private EmployeeRepository employeeRepository;
	
	public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	@Override
	public Employee saveEmployee(Employee employee) {
		
		Optional<Employee> savedEmployeeFromDB = employeeRepository.findByEmail(employee.getEmail());
		if(savedEmployeeFromDB.isPresent()) {
			throw new ResourceNotFoundException("Employee already exist with given email: " + employee.getEmail());
		}
		return employeeRepository.save(employee);
	}

	@Override
	public List<Employee> getAllEmployees() {
		
		return employeeRepository.findAll();
	}

	@Override
	public Optional<Employee> getEmployeeById(long id) {
		
		return employeeRepository.findById(id);
	}

	@Override
	public Employee updateEmployee(Employee updatedEmployee) {
		
		return employeeRepository.save(updatedEmployee);
	}

	@Override
	public void deleteEmployee(long id) {

		employeeRepository.deleteById(id);
	}

}
