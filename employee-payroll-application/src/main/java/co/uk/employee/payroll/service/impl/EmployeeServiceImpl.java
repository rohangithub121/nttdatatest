package co.uk.employee.payroll.service.impl;

import co.uk.employee.payroll.dto.EmployeeDetailsDTO;
import co.uk.employee.payroll.entity.EmployeeDetailsEntity;
import co.uk.employee.payroll.repository.EmployeeRepository;
import co.uk.employee.payroll.service.EmployeeService;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    @Resource
    private EmployeeRepository employeeRepository;

    @Override
    public EmployeeDetailsDTO saveEmployeeDetails(EmployeeDetailsDTO employeeDetailsDTO) {
        var savedEmployeeDetails = employeeRepository.save(createEmployeeDetailsEntity(employeeDetailsDTO));
        return employeeRepository.findById(savedEmployeeDetails.getId()).map(this::getEmployeeDetailsDTO).orElse(null);
    }

    @Override
    public Optional<EmployeeDetailsDTO> getEmployeeDetails(long employeeId) {
        return employeeRepository.findById(employeeId).map(this::getEmployeeDetailsDTO);
    }

    private EmployeeDetailsEntity createEmployeeDetailsEntity(EmployeeDetailsDTO employeeDetailsDTO) {
        return EmployeeDetailsEntity.builder()
                .name(employeeDetailsDTO.getName())
                .designation(employeeDetailsDTO.getDesignation())
                .projectName(employeeDetailsDTO.getProjectName())
                .grossSalary(new BigDecimal(employeeDetailsDTO.getGrossSalary()))
                .taxPercentage(new BigDecimal(employeeDetailsDTO.getTaxPercentage()))
                .pensionPercentage(new BigDecimal(employeeDetailsDTO.getPensionPercentage()))
                .additionalBenefitAmount(getAdditionalBenefitAmount(employeeDetailsDTO.getAdditionalBenefitAmount()))
                .build();
    }

    private EmployeeDetailsDTO getEmployeeDetailsDTO(EmployeeDetailsEntity employeeDetailsEntity) {
        return EmployeeDetailsDTO.builder()
                .name(employeeDetailsEntity.getName())
                .designation(employeeDetailsEntity.getDesignation())
                .projectName(employeeDetailsEntity.getProjectName())
                .grossSalary(employeeDetailsEntity.getGrossSalary().toString())
                .taxPercentage(employeeDetailsEntity.getTaxPercentage().toString())
                .pensionPercentage(employeeDetailsEntity.getPensionPercentage().toString())
                .additionalBenefitAmount(Optional.ofNullable(employeeDetailsEntity.getAdditionalBenefitAmount()).map(BigDecimal::toString).orElse(""))
                .netSalaryAmount(calculateNetSalary(employeeDetailsEntity))
                .build();
    }

    private BigDecimal calculateNetSalary(EmployeeDetailsEntity employeeDetailsEntity) {
        return employeeDetailsEntity.getGrossSalary()
                .subtract(getSalaryDeductions(employeeDetailsEntity))
                .add(getAdditionalBenefitAmount(employeeDetailsEntity.getAdditionalBenefitAmount()));
    }

    private BigDecimal getSalaryDeductions(EmployeeDetailsEntity employeeDetailsEntity) {
        return employeeDetailsEntity.getGrossSalary()
                .multiply(employeeDetailsEntity.getPensionPercentage().add(employeeDetailsEntity.getTaxPercentage()))
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }

    private BigDecimal getAdditionalBenefitAmount(BigDecimal additionalBenefitAmount) {
        return Optional.ofNullable(additionalBenefitAmount).orElse(BigDecimal.ZERO);
    }

    private BigDecimal getAdditionalBenefitAmount(String additionalBenefitAmount) {
        return Optional.ofNullable(additionalBenefitAmount).map(BigDecimal::new).orElse(BigDecimal.ZERO);
    }
}
