package co.uk.employee.payroll.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDetailsDTO {
    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Designation is mandatory")
    private String designation;

    @NotBlank(message = "Project Name is mandatory")
    private String projectName;

    @NotNull(message = "Gross salary is mandatory")
    @DecimalMin(value = "0.0", inclusive = false, message = "Gross salary should be greater than 0")
    private String grossSalary;

    @NotNull(message = "Tax percentage is mandatory")
    @DecimalMin(value = "0.0", inclusive = false, message = "Tax percentage should be greater than 0")
    private String taxPercentage;

    @NotNull(message = "Pension percentage is mandatory")
    @DecimalMin(value = "0.0", inclusive = false, message = "Pension percentage should be greater than 0")
    private String pensionPercentage;

    private String additionalBenefitAmount;

    private String netSalaryAmount;
}

