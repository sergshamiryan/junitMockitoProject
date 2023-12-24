package serg.shamiryan.junitmockhitoproject.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import serg.shamiryan.junitmockhitoproject.exceptions.ResourceNotFoundException;
import serg.shamiryan.junitmockhitoproject.model.Employee;
import serg.shamiryan.junitmockhitoproject.repository.EmployeeRepository;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.EMPTY_LIST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;

    @BeforeEach
    public void setup() {
//        this.employeeRepository = Mockito.mock(EmployeeRepository.class);
//        this.employeeService = new EmployeeServiceImpl(this.employeeRepository);
        this.employee = Employee.builder()
                .id(1)
                .firstName("Vardan")
                .lastName("Engibaryan")
                .email("vardEngibaryan@gmail.com")
                .build();
    }

    @Test
    @DisplayName("Test save employee method")
    public void givenEmployeeObject_whenSaveEmployee_thenSavedEmployee() {
        //given - precondition or setup
        given(
                employeeRepository
                        .findByEmail(employee.getEmail())).willReturn(Optional.empty());
        given(employeeRepository.save(employee)).willReturn(employee);
        //when - action or behaviour we are going to test
        Employee employeeDB = employeeService.saveEmployee(employee);
        //then - verify the output
        assertThat(employeeDB).isNotNull();
    }

    @Test
    @DisplayName("Test save employee method which throws exception")
    public void givenExistingEmil_whenSaveEmployee_thenThrowsException() {
        //given - precondition or setup
        given(
                employeeRepository
                        .findByEmail(employee.getEmail())).willReturn(Optional.of(employee));
        //when - action or behaviour we are going to test
        assertThrows(ResourceNotFoundException.class, () -> employeeService.saveEmployee(employee));
        //then - verify the output
        verify(employeeRepository,
                never()).save(any(Employee.class)); /*Verifying that the method never reach here*/
    }

    @Test
    @DisplayName("Test get all employees")
    public void givenListOfEmployeeObjects_whenFindAll_thenReturnEmployeeList() {
        //given - precondition or setup
        List<Employee> employees = List.of(
                employee, Employee
                        .builder()
                        .firstName("Vardan")
                        .lastName("Engibaryan")
                        .email("vardEngibaryan@gmail.com")
                        .build());
        given(employeeRepository.findAll()).willReturn(employees);
        //when - action or behaviour we are going to test
        List<Employee> employeeList = employeeService.getAllEmployees();
        //then - verify the output
        assertThat(employeeList).isNotEmpty();
    }

    @Test
    @DisplayName("Test get all employees (negative scenarion)")
    public void givenEmptyList_whenFindALl_thenReturnEmptyList() {
        //given - precondition or setup
        given(employeeRepository.findAll()).willReturn(EMPTY_LIST);
        //when - action or behaviour we are going to test
        List<Employee> employeeList = employeeService.getAllEmployees();
        //then - verify the output
        assertThat(employeeList).isEmpty();
    }

    @Test
    @DisplayName("Test get employee by id")
    public void givenId_whenGetEmployeeById_thenReturnEmployeeObject() {
        //given - precondition or setup
        given(employeeRepository.findById(1L)).willReturn(Optional.of(employee));
        //when - action or behaviour we are going to test
        Employee employeeDb = employeeService.getEmployeeById(employee.getId());
        //then - verify the output
        assertThat(employeeDb).isNotNull();
    }

    @Test
    @DisplayName("Test for updating an employee")
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee() {
        //given - precondition or setup
        given(employeeRepository.save(employee)).willReturn(employee);
        employee.setFirstName("Ramesh");
        employee.setLastName("Ramesh");
        employee.setEmail("rameshramesh@gmail.com");
        //when - action or behaviour we are going to test
        Employee employeeDb = employeeService.updateEmployee(employee);
        //then - verify the output
        assertThat(employeeDb.getFirstName()).isEqualTo("Ramesh");
    }

    @Test
    @DisplayName("Test for deleting an employee")
    public void given_when_then() {
        //given - precondition or setup
        willDoNothing().given(employeeRepository).deleteById(employee.getId());
        //when - action or behaviour we are going to test
        employeeService.deleteEmployee(employee.getId());
        //then - verify the output
        //In this example we don't have anything to verify, so we just have added this verification
        verify(employeeRepository, times(1)).deleteById(employee.getId());
    }
}