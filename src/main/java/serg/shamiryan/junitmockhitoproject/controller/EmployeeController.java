package serg.shamiryan.junitmockhitoproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import serg.shamiryan.junitmockhitoproject.model.Employee;
import serg.shamiryan.junitmockhitoproject.service.EmployeeServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeServiceImpl employeeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeService.saveEmployee(employee);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.CREATED)
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Employee getAllEmployeeById(@PathVariable long id) {
        return employeeService.getEmployeeById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Employee> updateAnEmployee(@PathVariable long id,
                                                     @RequestBody Employee newEmployee) {
        return employeeService.getEmployeeByIdOptional(id)
                .map(employeeDb -> {
                    employeeDb.setFirstName(newEmployee.getFirstName());
                    employeeDb.setLastName(newEmployee.getLastName());
                    employeeDb.setEmail(newEmployee.getEmail());
                    Employee updatedEmployee = employeeService.updateEmployee(employeeDb);
                    return new ResponseEntity<Employee>(updatedEmployee, HttpStatus.OK);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> deleteEmployee(@PathVariable long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok().build();
    }
}
