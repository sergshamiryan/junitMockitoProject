package serg.shamiryan.junitmockhitoproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import serg.shamiryan.junitmockhitoproject.exceptions.ResourceNotFoundException;
import serg.shamiryan.junitmockhitoproject.model.Employee;
import serg.shamiryan.junitmockhitoproject.repository.EmployeeRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl {
    private final EmployeeRepository employeeRepository;

    public Employee saveEmployee(Employee employee) {
        Optional<Employee> savedEmployee = employeeRepository.findByEmail(employee.getEmail());
        if (savedEmployee.isPresent()) {
            throw new ResourceNotFoundException(
                    "Employee already exists with given email : %s",
                    employee.getEmail());
        }
        return employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Not Found by id %d",
                        id));
    }

    public Employee updateEmployee(Employee updatedEmployee) {
        return employeeRepository.save(updatedEmployee);
    }

    public void deleteEmployee(long employeeId) {
        employeeRepository.deleteById(employeeId);
    }
}
