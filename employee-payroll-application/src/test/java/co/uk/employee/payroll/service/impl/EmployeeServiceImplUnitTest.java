package co.uk.employee.payroll.service.impl;

import co.uk.employee.payroll.dto.EmployeeDetailsDTO;
import co.uk.employee.payroll.entity.EmployeeDetailsEntity;
import co.uk.employee.payroll.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static co.uk.employee.payroll.constants.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmployeeServiceImplUnitTest {
    private final EmployeeRepository employeeRepositoryMock = Mockito.mock(EmployeeRepository.class);
    private EmployeeDetailsDTO mockEmployeeDetailsWithAdditionalAmountDTO;
    private EmployeeDetailsDTO mockEmployeeDetailsWithNoAdditionalAmountDTO;
    private EmployeeDetailsEntity mockEmployeeDetailsWithAdditionalAmountEntity;
    private EmployeeDetailsEntity mockEmployeeDetailsWithNoAdditionalAmountEntity;
    private final EmployeeServiceImpl testObj = new EmployeeServiceImpl(employeeRepositoryMock);

    @BeforeEach
    public void setUp() {
        mockEmployeeDetailsWithAdditionalAmountDTO = createMockEmployeeDetailsWithAdditionalAmountDTO();
        mockEmployeeDetailsWithNoAdditionalAmountDTO = createMockEmployeeDetailsWithNoAdditionalAmountDTO();

        mockEmployeeDetailsWithAdditionalAmountEntity = createMockEmployeeDetailsWithAdditionalAmountEntity();
        mockEmployeeDetailsWithNoAdditionalAmountEntity = createMockEmployeeDetailsWithNoAdditionalAmountEntity();
    }

    @Test
    public void testSaveEmployeeDetailsWhenAdditionalBenefitAmountIsPresent() {
        Mockito.when(employeeRepositoryMock.save(Mockito.any())).thenReturn(mockEmployeeDetailsWithAdditionalAmountEntity);
        Mockito.when(employeeRepositoryMock.findById(123L)).thenReturn(Optional.of(mockEmployeeDetailsWithAdditionalAmountEntity));

        var actualEmployeeDetailsDTO = testObj.saveEmployeeDetails(mockEmployeeDetailsWithAdditionalAmountDTO);

        checkAssertions(actualEmployeeDetailsDTO, mockEmployeeDetailsWithAdditionalAmountDTO);
    }

    @Test
    public void testSaveEmployeeDetailsWhenAdditionalBenefitAmountIsNotPresent() {
        Mockito.when(employeeRepositoryMock.save(Mockito.any())).thenReturn(mockEmployeeDetailsWithNoAdditionalAmountEntity);
        Mockito.when(employeeRepositoryMock.findById(MOCK_ID)).thenReturn(Optional.of(mockEmployeeDetailsWithNoAdditionalAmountEntity));

        var actualEmployeeDetailsDTO = testObj.saveEmployeeDetails(mockEmployeeDetailsWithNoAdditionalAmountDTO);

        checkAssertions(actualEmployeeDetailsDTO, mockEmployeeDetailsWithNoAdditionalAmountDTO);
    }

    @Test
    public void testGetEmployeeDetails() {
        Mockito.when(employeeRepositoryMock.findAll(Sort.by(Sort.Direction.ASC, "id")))
                .thenReturn(List.of(mockEmployeeDetailsWithAdditionalAmountEntity, mockEmployeeDetailsWithNoAdditionalAmountEntity));

        var employees = testObj.getEmployeesDetail();
        assertEquals(employees.size(), 2);

        checkAssertions(employees.get(0), mockEmployeeDetailsWithAdditionalAmountDTO);
        checkAssertions(employees.get(1), mockEmployeeDetailsWithNoAdditionalAmountDTO);
    }

    private void checkAssertions(EmployeeDetailsDTO actualEmployeeDetailsDTO, EmployeeDetailsDTO mockEmployeeDetailsDTO) {
        assertEquals(actualEmployeeDetailsDTO.getName(), mockEmployeeDetailsDTO.getName());
        assertEquals(actualEmployeeDetailsDTO.getDesignation(), mockEmployeeDetailsDTO.getDesignation());
        assertEquals(actualEmployeeDetailsDTO.getProjectName(), mockEmployeeDetailsDTO.getProjectName());
        assertEquals(actualEmployeeDetailsDTO.getGrossSalary(), mockEmployeeDetailsDTO.getGrossSalary());
        assertEquals(actualEmployeeDetailsDTO.getTaxPercentage(), mockEmployeeDetailsDTO.getTaxPercentage());
        assertEquals(actualEmployeeDetailsDTO.getPensionPercentage(), mockEmployeeDetailsDTO.getPensionPercentage());
        assertEquals(actualEmployeeDetailsDTO.getNetSalaryAmount(), mockEmployeeDetailsDTO.getNetSalaryAmount());
    }

    private EmployeeDetailsDTO createMockEmployeeDetailsWithAdditionalAmountDTO() {
        var employeeDetailsDTO = createMockEmployeeDetailsWithNoAdditionalAmountDTO();
        employeeDetailsDTO.setAdditionalBenefitAmount(ADDITIONAL_BENEFIT_AMOUNT);
        employeeDetailsDTO.setNetSalaryAmount(NET_SALARY_AMOUNT_WITH_ADDITIONAL_BENEFIT);
        return employeeDetailsDTO;
    }

    private EmployeeDetailsDTO createMockEmployeeDetailsWithNoAdditionalAmountDTO() {
        return EmployeeDetailsDTO.builder()
                .name(MOCK_NAME)
                .designation(MOCK_ENGINEER)
                .projectName(MOCK_PROJECT_NAME)
                .grossSalary(MOCK_GROSS_SALARY)
                .taxPercentage(MOCK_TAX_PERCENTAGE)
                .pensionPercentage(MOCK_PENSION_PERCENTAGE)
                .netSalaryAmount(MOCK_NET_SALARY_AMOUNT_WITH_NO_ADDITIONAL_BENEFITS)
                .build();
    }

    private EmployeeDetailsEntity createMockEmployeeDetailsWithAdditionalAmountEntity() {
        var employeeDetailsEntity = createMockEmployeeDetailsWithNoAdditionalAmountEntity();
        employeeDetailsEntity.setAdditionalBenefitAmount(new BigDecimal(ADDITIONAL_BENEFIT_AMOUNT));
        return employeeDetailsEntity;
    }

    private EmployeeDetailsEntity createMockEmployeeDetailsWithNoAdditionalAmountEntity() {
        return EmployeeDetailsEntity.builder()
                .id(MOCK_ID)
                .name(MOCK_NAME)
                .designation(MOCK_ENGINEER)
                .projectName(MOCK_PROJECT_NAME)
                .grossSalary(new BigDecimal(MOCK_GROSS_SALARY))
                .taxPercentage(new BigDecimal(MOCK_TAX_PERCENTAGE))
                .pensionPercentage(new BigDecimal(MOCK_PENSION_PERCENTAGE))
                .build();
    }
}
