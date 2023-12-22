package com.shah.employees.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.shah.employees.model.Employee;

/**
 * @author Shahrukh
 *
 */
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	Optional<Employee> findByEmail(String email); //findBy"Email" must match with model data member

	//custom query using JPQL with indexed parameters
	@Query("select e from Employee e where e.firstName= ?1 and e.lastName= ?2")
	Employee findByJPQLIndexedParams(String firstName, String lastName);

	//custom query using JPQL with named parameters
	@Query("select e from Employee e where e.firstName= :fname and e.lastName= :lname")
	Employee findByJPQLNamedParams(@Param("fname") String firstName, @Param("lname") String lastName);
	
	//custom native query with indexed parameters
	@Query(value = "select * from employees e where e.email= ?1", nativeQuery = true)
	Employee findByNativeSQLIndexedParam(String email);
	
	//custom native query with named parameters
	@Query(value = "select * from employees e where e.email = :searchEmail", nativeQuery = true)
	Employee findByNativeSQLNamedParam(@Param("searchEmail") String email);
}

/*
 * no need to provide @Repository class-level annotation because JpaRepository interface has 
 * an implementation class called SimpleJpaRepository that uses @Repository annotation 
 * 
 * And same is applied for @Transactional hence, no need to use it in Service layer
 */