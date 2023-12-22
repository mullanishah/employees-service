package com.shah.employees.repository;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.shah.employees.model.Employee;

/**
 * @author Shahrukh
 *
 */
@DataJpaTest
public class RefactoredEmployeeRepositoryTest {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	private Employee employee, employeeTwo;

	@BeforeEach
	public void setup() {
		employee = Employee.builder()
				.firstName("Chandler")
				.lastName("Bing")
				.email("sarcastic.bing@yahoo.com")
				.build();
		employeeTwo = Employee.builder()
				.firstName("Joey")
				.lastName("Tribbiani")
				.email("joeyDoesNotShareHisFood@gmail.com")
				.build();
	}

	//JUnit test for save employee operation
	@DisplayName("JUnit test for save employee operation")
	@Test
	public void givenEmployeeObject_whenSave_thenReturnSavedEmployee() {
		//given- precondition or setup
		
		//when- action or behaviour that we are testing
		Employee savedEmployee = employeeRepository.save(employee);

		//then- verify the output
		assertThat(savedEmployee).isNotNull();
		assertThat(savedEmployee.getId()).isGreaterThan(0); //Assertions-static import
	}

	//JUnit test for get all employees operation
	@DisplayName("JUnit test for get all employees operation")
	@Test
	public void givenNothing_whenFindAll_thenEmployeesList() {
		//given- precondition or setup
		employeeRepository.save(employee);
		employeeRepository.save(employeeTwo);

		//when- action or behaviour that we are testing
		List<Employee> employeeList = employeeRepository.findAll();

		//then- verify the output
		assertThat(employeeList).isNotEmpty();
		assertThat(employeeList.size()).isEqualTo(2);
	}

	//JUnit test for get employee by id operation
	@DisplayName("JUnit test for get employee by id operation")
	@Test
	public void givenEmployeeObject_whenFindById_thenSavedEmployee() {
		//given- precondition or setup
		employeeRepository.save(employee);

		//when- action or behaviour that we are testing
		Employee savedEmployee = employeeRepository.findById(employee.getId()).get();

		//then- verify the output
		assertThat(savedEmployee).isNotNull();
	}

	//JUnit test for get Employee by email operation
	@DisplayName("JUnit test for get Employee by email operation")
	@Test
	public void givenEmployeeObject_whenFindByEmail_thenSavedEmployee() {
		//given- precondition or setup
		employeeRepository.save(employee);

		//when- action or behaviour that we are testing
		Employee savedEmployee = employeeRepository.findByEmail(employee.getEmail()).get();

		//then- verify the output
		assertThat(savedEmployee).isNotNull();
	}

	//JUnit test for update employee operation
	@DisplayName("JUnit test for update employee operation")
	@Test
	public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee() {
		//given- precondition or setup
		employeeRepository.save(employee);

		//when- action or behaviour that we are testing
		Employee savedEmployee = employeeRepository.findById(employee.getId()).get();
		savedEmployee.setEmail("simplyRachel@gmail.com");
		savedEmployee.setFirstName("Rachell");
		Employee updatedEmployee = employeeRepository.save(savedEmployee);

		//then- verify the output
		assertThat(updatedEmployee.getEmail()).isEqualTo("simplyRachel@gmail.com");
		assertThat(updatedEmployee.getFirstName()).isEqualTo("Rachell");
	}

	//JUnit test for delete employee operation
	@DisplayName("JUnit test for delete employee operation")
	@Test
	public void givenEmployeeObject_whenDelete_thenCheckDeletedEmployee() {
		//given- precondition or setup
		employeeRepository.save(employee);

		//when- action or behaviour that we are testing
		//employeeRepository.delete(employee);
		//or
		employeeRepository.deleteById(employee.getId());

		Optional<Employee> deletedEmployee = employeeRepository.findById(employee.getId());

		//then- verify the output
		assertThat(deletedEmployee).isEmpty();
	}

	//JUnit test for Spring Data JPA custom query using JPQL with indexed parameters
	@DisplayName("JUnit test for custom JPQL query with indexed parameters")
	@Test
	public void givenFirstAndLastName_whenFindByJPQLIndexedParams_thenCheckSavedEmployee() {
		//given- precondition or setup
		employeeRepository.save(employee);
		String searchFirstName = employee.getFirstName();
		String searchLastName = employee.getLastName();

		//when- action or behaviour that we are testing
		Employee savedEmployee = employeeRepository.findByJPQLIndexedParams(searchFirstName, searchLastName);

		//then- verify the output
		assertThat(savedEmployee).isNotNull();
	}

	//JUnit test for Spring Data JPA custom query using JPQL with indexed parameters
	@DisplayName("JUnit test for custom JPQL query with named parameters")
	@Test
	public void givenFirstAndLastName_whenFindByJPQLNamedParams_thenCheckSavedEmployee() {
		//given- precondition or setup
		employeeRepository.save(employee);
		String searchFirstName = employee.getFirstName();
		String searchLastName = employee.getLastName();

		//when- action or behaviour that we are testing
		Employee savedEmployee = employeeRepository.findByJPQLNamedParams(searchFirstName, searchLastName);

		//then- verify the output
		assertThat(savedEmployee).isNotNull();
	}

	//JUnit test for Spring data JPA custom native query with indexed parameters
	@DisplayName("JUnit test for custom native query with indexed parameters")
	@Test
	public void givenEmail_whenFindByNativeSQLIndexedParam_thenCheckSavedEmployee() {
		//given- precondition or setup
		employeeRepository.save(employee);
		String searchEmail = employee.getEmail();

		//when- action or behaviour that we are testing
		Employee savedEmployee = employeeRepository.findByNativeSQLIndexedParam(searchEmail);

		//then- verify the output
		assertThat(savedEmployee).isNotNull();
	}

	//JUnit test for Spring data JPA custom native query with named parameters
	@DisplayName("JUnit test for custom native query with named parameters")
	@Test
	public void givenEmail_whenFindByNativeSQLNamedParam_thenCheckSavedEmployee() {
		//given- precondition or setup
		employeeRepository.save(employee);
		String searchEmail = employee.getEmail();

		//when- action or behaviour that we are testing
		Employee savedEmployee = employeeRepository.findByNativeSQLNamedParam(searchEmail);

		//then- verify the output
		assertThat(savedEmployee).isNotNull();
	}

}
