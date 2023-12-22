package com.shah.employees.service;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import com.shah.employees.model.Employee;
import com.shah.employees.repository.EmployeeRepository;

/**
 * @author Shahrukh
 * @since 14/12/2023
 */
public class EmployeeServiceTest {
	
	private EmployeeRepository employeeRepository;
	private EmployeeService employeeService;
	
	@BeforeEach
	public void setup() {
		employeeRepository = Mockito.mock(EmployeeRepository.class);
		employeeService = new EmployeeServiceImpl(employeeRepository);
	}
	
	//JUnit test for saveEmployee method
	@DisplayName(value = "JUnit test for saveEmployee method")
	@Test
	public void givenEmployeeObject_whenSaveEmployee_thenReturnSavedEmployee() {
		//given- precondition or setup
		Employee employee = Employee.builder()
				.id(1L)
				.firstName("Mahendra Singh")
				.lastName("Dhoni")
				.email("caption.cool07@yahoo.com")
				.build();
		
		BDDMockito.given(employeeRepository.findByEmail(Mockito.anyString())).willReturn(Optional.empty());
		
		BDDMockito.given(employeeRepository.save(employee)).willReturn(employee);
		
		System.out.println(employeeRepository);
		System.out.println(employeeService);
		
		//when- action or behaviour that we are testing
		Employee savedEmployee = employeeService.saveEmployee(employee);
		
		//then- verify the output
		Assertions.assertThat(savedEmployee).isNotNull();
	}

}
