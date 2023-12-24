package serg.shamiryan.junitmockhitoproject.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import serg.shamiryan.junitmockhitoproject.model.Employee;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.given;

@DataJpaTest
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;

    @BeforeEach
    public void setup() {
        this.employee = Employee
                .builder()
                .firstName("Sergey")
                .lastName("Shamiryan")
                .email("sergshamiryan@gmail.com")
                .build();
    }

    @Test
    @DisplayName("Test for saving employee")
    public void givenEmployeeObject_whenSave_thenReturnSavedEmployee() {
        //given - precondition or setup
        //when - action or behaviour
        Employee savedEmployee = employeeRepository.save(employee);
        //then - verify the output
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);
    }

    @Test
    @DisplayName("Test for all employees")
    public void givenEmployeeList_whenFindAll_thenReturnEmployeeList() {
        //given - precondition or setup
        Employee employee1 = Employee
                .builder()
                .firstName("Armen")
                .lastName("Vardnyan")
                .email("armVardanyn@gmail.com")
                .build();
        employeeRepository.save(employee);
        employeeRepository.save(employee1);
        //when - action or behaviour we are going to test
        List<Employee> employees = employeeRepository.findAll();
        //Then - verify the output
        assertThat(employees).isNotEmpty();
        assertThat(employees.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Test get employee by id")
    public void givenEmployeeObject_whenFindById_thenReturnEmployeeObject() {
        //given - precondition or setup
        Employee employee = Employee
                .builder()
                .firstName("Armen")
                .lastName("Vardnyan")
                .email("armVardanyn@gmail.com")
                .build();
        employeeRepository.save(employee);
        //when - action or behaviour we are going to test
        Employee employeeDB = employeeRepository.findById(employee.getId()).get();
        //then - verify the output
        assertThat(employeeDB).isNotNull();
    }

    @Test
    @DisplayName("Get Employee by email")
    public void givenEmployeeObject_whenFindByEmail_thenReturnEmployeeObject() {
        //given - precondition or setup
        Employee employee = Employee
                .builder()
                .firstName("Armen")
                .lastName("Vardnyan")
                .email("armVardanyn@gmail.com")
                .build();
        employeeRepository.save(employee);
        //when - action or behaviour we are going to test
        Employee employeeDb = employeeRepository.findByEmail(employee.getEmail()).get();
        //then - verify the output
        assertThat(employeeDb).isNotNull();
    }

    @Test
    @DisplayName("Test update employee")
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnEmployeeObject() {
        //given - precondition or setup
        Employee employee = Employee
                .builder()
                .firstName("Armen")
                .lastName("Vardnyan")
                .email("armVardanyn@gmail.com")
                .build();
        employeeRepository.save(employee);
        //when - action or behaviour we are going to test
        Employee employeeDb = employeeRepository.findById(employee.getId()).get();
        employeeDb.setEmail("armenVardanyan@gmail.com");
        Employee employeeUpdated = employeeRepository.save(employeeDb);
        //then - verify the output
        assertThat(employeeUpdated.getEmail()).isEqualTo("armenVardanyan@gmail.com");
    }

    @DisplayName("JUnit test for delete employee operation")
    @Test
    public void givenEmployeeObject_whenDelete_thenRemoveEmployee() {
        // given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("Ramesh")
                .lastName("Fadatare")
                .email("ramesh@gmail,com")
                .build();
        employeeRepository.save(employee);

        // when -  action or the behaviour that we are going test
        employeeRepository.deleteById(employee.getId());
        Optional<Employee> employeeOptional = employeeRepository.findById(employee.getId());

        // then - verify the output
        assertThat(employeeOptional).isEmpty();
    }

    @DisplayName("JUnit test for finding by name")
    @Test
    public void givenFirstNameAndLastName_whenFindByJpql_thenReturnEmployeeObject() {
        //given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("Ramesh")
                .lastName("Fadatare")
                .email("ramesh@gmail,com")
                .build();
        employeeRepository.save(employee);
        //when - action or behaviour we are going to test
        Employee employeeDb =
                employeeRepository.findByJPQL(employee.getFirstName(), employee.getLastName());
        Employee employeeDb2 =
                employeeRepository.findByJPQL(employee.getFirstName(), "Ram");
        //then - verify the output
        assertThat(employeeDb).isNotNull();
        assertThat(employeeDb2).isNull();
    }

    // JUnit test for custom query using native SQL with index
    @DisplayName("JUnit test for custom query using native SQL with index")
    @Test
    public void givenFirstNameAndLastName_whenFindByNativeSQL_thenReturnEmployeeObject() {
        // given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("Ramesh")
                .lastName("Fadatare")
                .email("ramesh@gmail,com")
                .build();
        employeeRepository.save(employee);
        // String firstName = "Ramesh";
        // String lastName = "Fadatare";

        // when -  action or the behaviour that we are going test
        Employee savedEmployee = employeeRepository.findByNativeSQL(employee.getFirstName(),
                employee.getLastName());

        // then - verify the output
        assertThat(savedEmployee).isNotNull();
    }

    // JUnit test for custom query using native SQL with named params
    @DisplayName("JUnit test for custom query using native SQL with named params")
    @Test
    public void givenFirstNameAndLastName_whenFindByNativeSQLNamedParams_thenReturnEmployeeObject() {
        // given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("Ramesh")
                .lastName("Fadatare")
                .email("ramesh@gmail,com")
                .build();
        employeeRepository.save(employee);
        // String firstName = "Ramesh";
        // String lastName = "Fadatare";

        // when -  action or the behaviour that we are going test
        Employee savedEmployee = employeeRepository.findByNativeSQLNamed(employee.getFirstName(),
                employee.getLastName());

        // then - verify the output
        assertThat(savedEmployee).isNotNull();
    }
}