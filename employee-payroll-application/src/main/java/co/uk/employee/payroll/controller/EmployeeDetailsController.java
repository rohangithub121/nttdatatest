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

    @ApiOperation(value = "Gets employee details from database", response = EmployeeDetailsDTO.class)
    @GetMapping("/employees-detail/{employeeId}")
    public ResponseEntity<EmployeeDetailsDTO> getEmployeeDetails(@PathVariable(name = "employeeId") final long employeeId) {
        return employeeService.getEmployeeDetails(employeeId)
                .map(employeeDetailsDTO -> new ResponseEntity<>(employeeDetailsDTO, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
