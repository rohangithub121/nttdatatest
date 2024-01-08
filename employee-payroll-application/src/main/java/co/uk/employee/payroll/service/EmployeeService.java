package co.uk.employee.payroll.service;

import co.uk.employee.payroll.dto.EmployeeDetailsDTO;

import java.util.List;

public interface EmployeeService {
    /**
     * Saves the given employee details to persistent storage.
     *
     * @param employeeDetailsDTO employee details
     * @return employeeDetailsDTO that was persisted
     */
    EmployeeDetailsDTO saveEmployeeDetails(EmployeeDetailsDTO employeeDetailsDTO);

    /**
     * Gets all the employee details present in the system.
     *
     * @return list of employeeDetailsDTO present in the system
     */
    List<EmployeeDetailsDTO> getEmployeesDetail();
}
