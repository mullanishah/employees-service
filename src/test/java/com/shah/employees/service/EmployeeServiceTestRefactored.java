package com.shah.employees.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.shah.employees.exception.ResourceNotFoundException;
import com.shah.employees.model.Employee;
import com.shah.employees.repository.EmployeeRepository;

/**
 * @author Shahrukh
 * @since 14/12/2023
 */
@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTestRefactored {

	@Mock
	private EmployeeRepository employeeRepository;
	@InjectMocks
	private EmployeeServiceImpl employeeService;
	
	private Employee employee, employeeTwo;

	@BeforeEach
	public void setup() {
		employee = Employee.builder()
				.id(1L)
				.firstName("Mahendra Singh")
				.lastName("Dhoni")
				.email("caption.cool07@yahoo.com")
				.build();
		employeeTwo = Employee.builder()
				.id(1L)
				.firstName("Gautam")
				.lastName("Gambhir")
				.email("gauti23@gmail.com")
				.build();
	}

	//JUnit test for saveEmployee method
	@DisplayName(value = "JUnit test for saveEmployee method")
	@Test
	public void givenEmployeeObject_whenSaveEmployee_thenReturnSavedEmployee() {
		//given- precondition or setup
		given(employeeRepository.findByEmail(Mockito.anyString())).willReturn(Optional.empty());
		given(employeeRepository.save(employee)).willReturn(employee);

		//when- action or behaviour that we are testing
		Employee savedEmployee = employeeService.saveEmployee(employee);

		//then- verify the output
		assertThat(savedEmployee).isNotNull();
		assertThat(savedEmployee.getId()).isPositive();
	}
	
	//JUnit test for saveEmployee method which throws an exception
	@DisplayName("JUnit test for saveEmployee method which throws an exception")
	@Test
	public void givenExistingEmployee_whenSaveEmployee_thenThrowsException() {
		//given- precondition or setup
		given(employeeRepository.findByEmail(Mockito.anyString())).willReturn(Optional.of(employee));
		
		//given(employeeRepository.save(employee)).willReturn(employee); //UnnecessaryStubbingException: Unnecessary stubbings detected.
		
		//when- action or behaviour that we are testing
		assertThrows(ResourceNotFoundException.class, () -> { employeeService.saveEmployee(employee); });
		
		//then- verify the output
		Mockito.verify(employeeRepository, Mockito.never()).save(Mockito.any(Employee.class));
	}
	
	//JUnit test for getAllEmployees method
	@DisplayName(value = "JUnit test for getAllEmployees method (Positive scenario)")
	@Test
	public void givenEmployeeList_whenGetAllEmployees_thenReturnEmployeeList() {
		//given- precondition or setup
		given(employeeRepository.findAll()).willReturn(List.of(employee, employeeTwo));
		
		//when- action or behaviour that we are testing
		List<Employee> employeeList = employeeService.getAllEmployees();
		
		//then- verify the output
		assertThat(employeeList).isNotNull();
		assertThat(employeeList.size()).isEqualTo(2);
	}
	
	//JUnit test for getAllEmployees method
	@DisplayName("JUnit test for getAllEmployees method (Negative scenario)")
	@Test
	public void givenEmptyList_whenGetAllEmployees_thenReturnEmptyEmployeeList() {
		//given- precondition or setup
		given(employeeRepository.findAll()).willReturn(Collections.emptyList());
		
		//when- action or behaviour that we are testing
		List<Employee> employeeList = employeeService.getAllEmployees();
		
		//then- verify the output
		assertThat(employeeList).isNotNull();
		assertThat(employeeList.size()).isEqualTo(0);
	}
	
	//JUnit test for getEmployeeById method
	@DisplayName("JUnit test for getEmployeeById method")
	@Test
	public void givenEmployeeId_whenGetEmployeeById_thenReturnSavedEmployee() {
		//given- precondition or setup
		given(employeeRepository.findById(Mockito.anyLong())).willReturn(Optional.of(employee));
		
		//when- action or behaviour that we are testing
		Employee savedEmployee = employeeService.getEmployeeById(Mockito.anyLong()).get();
		
		//then- verify the output
		assertThat(savedEmployee).isNotNull();
		assertThat(savedEmployee.getId()).isGreaterThan(0);
	}
	
	//JUnit test for updateEmployee method
	@DisplayName("JUnit test for updateEmployee method")
	@Test
	public void givenUpdatedValues_whenUpdateEmployee_thenReturnUpdatedEmployee() {
		//given- precondition or setup
		given(employeeRepository.save(employee)).willReturn(employee);
		employee.setFirstName("Sachin");
		employee.setEmail("master.blaster@yahoo.com");
		
		//when- action or behaviour that we are testing
		Employee updatedEmployee = employeeService.updateEmployee(employee);
		
		//then- verify the output
		assertThat(updatedEmployee.getFirstName()).isEqualTo("Sachin");
		assertThat(updatedEmployee.getEmail()).isEqualTo("master.blaster@yahoo.com");
	}
	
	//JUnit test for deleteEmployee method
	@DisplayName("JUnit test for deleteEmployee method")
	@Test
	public void givenEmployeeId_whenDeleteEmployee_thenReturnNothing() {
		//given- precondition or setup
		long employeeIdToDelete = 1L;
		BDDMockito.willDoNothing().given(employeeRepository).deleteById(Mockito.anyLong());
		
		//when- action or behaviour that we are testing
		employeeService.deleteEmployee(employeeIdToDelete);
		
		//then- verify the output
		BDDMockito.verify(employeeRepository, Mockito.times(1)).deleteById(employeeIdToDelete);
	}

}
