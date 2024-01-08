package co.uk.employee.payroll.controller;

import co.uk.employee.payroll.dto.EmployeeDetailsDTO;
import co.uk.employee.payroll.service.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/employee/payroll")
@Api(value = "Employee details Controller")
public class EmployeeDetailsController {
    @Resource
    private EmployeeService employeeService;

    @ApiOperation(value = "Saves employee details in database", response = EmployeeDetailsDTO.class)
    @PostMapping
    public ResponseEntity<EmployeeDetailsDTO> saveEmployeeDetails(@Valid @RequestBody EmployeeDetailsDTO employeeDetailsDTO) {
        return new ResponseEntity<>(employeeService.saveEmployeeDetails(employeeDetailsDTO), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Gets employees detail from database", response = List.class)
    @GetMapping("/employees-detail")
    public List<EmployeeDetailsDTO> getEmployeeDetail() {
        return employeeService.getEmployeesDetail();
    }
}
