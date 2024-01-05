package co.uk.employee.payroll.service;

import co.uk.employee.payroll.dto.EmployeeDetailsDTO;

import java.util.Optional;

public interface EmployeeService {
    /**
     * Saves the given employee details to persistent storage.
     *
     * @param employeeDetailsDTO employee details
     * @return employeeDetailsDTO that was persisted
     */
    EmployeeDetailsDTO saveEmployeeDetails(EmployeeDetailsDTO employeeDetailsDTO);

    /**
     * Gets the employee details for the given employee id.
     *
     * @param employeeId employee id
     * @return employeeDetailsDTO for the given employee id
     */
    Optional<EmployeeDetailsDTO> getEmployeeDetails(long employeeId);
}
