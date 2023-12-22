package com.shah.employees.service;

import java.util.List;
import java.util.Optional;
import com.shah.employees.model.Employee;

/**
 * @author Shahrukh
 *
 */
public interface EmployeeService {
	
	Employee saveEmployee(Employee employee);
	
	List<Employee> getAllEmployees();
	
	Optional<Employee> getEmployeeById(long id);
	
	Employee updateEmployee(Employee updatedEmployee);
	
	void deleteEmployee(long id);
	
}
