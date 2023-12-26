package serg.shamiryan.junitmockhitoproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import serg.shamiryan.junitmockhitoproject.model.Employee;
import serg.shamiryan.junitmockhitoproject.service.EmployeeServiceImpl;

import java.util.List;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeServiceImpl employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    private Employee employee;

    @BeforeEach
    public void setup() {
        this.employee = Employee.builder()
                .firstName("Ramesh")
                .lastName("Ro")
                .email("rameshRo@gmail.com")
                .build();
    }

    @Test
    @DisplayName("Test creating an employee")
    public void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee() throws Exception {
        //given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("Ramesh")
                .lastName("Ro")
                .email("rameshRo@gmail.com")
                .build();
        BDDMockito.given(employeeService.saveEmployee(ArgumentMatchers.any(Employee.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));
        //when - action or behaviour we are going to test
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));
        //then - verify the output
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.firstName", CoreMatchers.is(employee.getFirstName())))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.lastName", CoreMatchers.is(employee.getLastName())));
    }

    @Test
    @DisplayName("Test getting all Employees")
    public void givenEmployeeList_whenGetAllEmployees_thenReturnAllEmployeesObject()
            throws Exception {
        //given - precondition or setup
        List<Employee> employees = List.of(employee, Employee.builder()
                .firstName("Sergey")
                .lastName("Shamiryan")
                .email("sergeyShamiryan@gmail.com")
                .build());
        BDDMockito.given(employeeService.getAllEmployees())
                .willReturn(employees);
        //when - action or behaviour we are going to test
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employees)));
        //then - verify the output
        result.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.size()", CoreMatchers.is(2)));
    }

    @Test
    @DisplayName("Test for get user by id")
    public void givenId_whenGetById_thenReturnEmployeeObject() throws Exception {
        //given - precondition or setup
        long id = 1;
        BDDMockito.given(employeeService.getEmployeeById(1)).willReturn(employee);
        //when - action or behaviour we are going to test
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/employees/{id}",id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));
        //then - verify the output
        result.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}