package co.uk.employee.payroll.integration;

import co.uk.employee.payroll.dto.EmployeeDetailsDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static co.uk.employee.payroll.constants.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EmployeePayrollApplicationIntegrationTest {

    private EmployeeDetailsDTO mockEmployeeDetailsWithAdditionalAmountDTO;
    private EmployeeDetailsDTO mockEmployeeDetailsWithNoAdditionalAmountDTO;

    private EmployeeDetailsDTO mockEmployeeDetailsWithEmptyNameDTO;

    private EmployeeDetailsDTO mockEmployeeDetailsWithEmptyDesignationDTO;

    private EmployeeDetailsDTO mockEmployeeDetailsWithEmptyProjectNameDTO;

    private EmployeeDetailsDTO mockEmployeeDetailsWithEmptyGrossSalaryDTO;

    private EmployeeDetailsDTO mockEmployeeDetailsWithEmptyTaxPercentDTO;

    private EmployeeDetailsDTO mockEmployeeDetailsWithEmptyPensionPercentDTO;

    @BeforeEach
    public void setUp() {
        mockEmployeeDetailsWithAdditionalAmountDTO = createMockEmployeeDetailsDTO();

        mockEmployeeDetailsWithNoAdditionalAmountDTO = createMockEmployeeDetailsDTO();
        mockEmployeeDetailsWithNoAdditionalAmountDTO.setAdditionalBenefitAmount(ZERO);
        mockEmployeeDetailsWithNoAdditionalAmountDTO.setNetSalaryAmount(MOCK_NET_SALARY_AMOUNT_WITH_NO_ADDITIONAL_BENEFITS);

        mockEmployeeDetailsWithEmptyNameDTO = createMockEmployeeDetailsDTO();
        mockEmployeeDetailsWithEmptyNameDTO.setName(null);

        mockEmployeeDetailsWithEmptyDesignationDTO = createMockEmployeeDetailsDTO();
        mockEmployeeDetailsWithEmptyDesignationDTO.setDesignation(null);

        mockEmployeeDetailsWithEmptyProjectNameDTO = createMockEmployeeDetailsDTO();
        mockEmployeeDetailsWithEmptyProjectNameDTO.setProjectName(null);

        mockEmployeeDetailsWithEmptyGrossSalaryDTO = createMockEmployeeDetailsDTO();
        mockEmployeeDetailsWithEmptyGrossSalaryDTO.setGrossSalary(null);

        mockEmployeeDetailsWithEmptyTaxPercentDTO = createMockEmployeeDetailsDTO();
        mockEmployeeDetailsWithEmptyTaxPercentDTO.setTaxPercentage(null);

        mockEmployeeDetailsWithEmptyPensionPercentDTO = createMockEmployeeDetailsDTO();
        mockEmployeeDetailsWithEmptyPensionPercentDTO.setPensionPercentage(null);
    }

    @Test
    public void testGetEmployeeDetailsWhenEmployeeNotPresent(@Autowired final WebTestClient webTestClient) {
        webTestClient.get()
                .uri(GET_EMPLOYEE_DETAILS_URL)
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    public void testSaveEmployeeDetailsWithEmptyName(@Autowired final WebTestClient webTestClient) {
        webTestClient.post()
                .uri(POST_EMPLOYEE_PAYLOAD_PATH)
                .bodyValue(mockEmployeeDetailsWithEmptyNameDTO)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    public void testSaveEmployeeDetailsWithEmptyDesignation(@Autowired final WebTestClient webTestClient) {
        webTestClient.post()
                .uri(POST_EMPLOYEE_PAYLOAD_PATH)
                .bodyValue(mockEmployeeDetailsWithEmptyDesignationDTO)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    public void testSaveEmployeeDetailsWithEmptyProjectName(@Autowired final WebTestClient webTestClient) {
        webTestClient.post()
                .uri(POST_EMPLOYEE_PAYLOAD_PATH)
                .bodyValue(mockEmployeeDetailsWithEmptyProjectNameDTO)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    public void testSaveEmployeeDetailsWithEmptyGrossSalary(@Autowired final WebTestClient webTestClient) {
        webTestClient.post()
                .uri(POST_EMPLOYEE_PAYLOAD_PATH)
                .bodyValue(mockEmployeeDetailsWithEmptyGrossSalaryDTO)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    public void testSaveEmployeeDetailsWithEmptyTaxPercent(@Autowired final WebTestClient webTestClient) {
        webTestClient.post()
                .uri(POST_EMPLOYEE_PAYLOAD_PATH)
                .bodyValue(mockEmployeeDetailsWithEmptyTaxPercentDTO)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    public void testSaveEmployeeDetailsWithEmptyPensionPercent(@Autowired final WebTestClient webTestClient) {
        webTestClient.post()
                .uri(POST_EMPLOYEE_PAYLOAD_PATH)
                .bodyValue(mockEmployeeDetailsWithEmptyPensionPercentDTO)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    public void testSaveEmployeeDetailsWithAdditionalBenefitAmount(@Autowired final WebTestClient webTestClient) {
        webTestClient.post()
                .uri(POST_EMPLOYEE_PAYLOAD_PATH)
                .bodyValue(mockEmployeeDetailsWithAdditionalAmountDTO)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(EmployeeDetailsDTO.class)
                .value(this::verifyAdditionalBenefitsAmount);
    }

    @Test
    public void testSaveEmployeeDetailsWithNoAdditionalBenefitAmount(@Autowired final WebTestClient webTestClient) {
        webTestClient.post()
                .uri(POST_EMPLOYEE_PAYLOAD_PATH)
                .bodyValue(mockEmployeeDetailsWithNoAdditionalAmountDTO)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(EmployeeDetailsDTO.class)
                .value(this::verifyNoAdditionalBenefitsAmount);
    }

    private void verifyAdditionalBenefitsAmount(EmployeeDetailsDTO employeeDetailsDTO) {
        assertEquals(employeeDetailsDTO.getName(), mockEmployeeDetailsWithAdditionalAmountDTO.getName());
        assertEquals(employeeDetailsDTO.getDesignation(), mockEmployeeDetailsWithAdditionalAmountDTO.getDesignation());
        assertEquals(employeeDetailsDTO.getProjectName(), mockEmployeeDetailsWithAdditionalAmountDTO.getProjectName());
        assertEquals(employeeDetailsDTO.getGrossSalary(), mockEmployeeDetailsWithAdditionalAmountDTO.getGrossSalary());
        assertEquals(employeeDetailsDTO.getTaxPercentage(), mockEmployeeDetailsWithAdditionalAmountDTO.getTaxPercentage());
        assertEquals(employeeDetailsDTO.getPensionPercentage(), mockEmployeeDetailsWithAdditionalAmountDTO.getPensionPercentage());
        assertEquals(employeeDetailsDTO.getAdditionalBenefitAmount(), mockEmployeeDetailsWithAdditionalAmountDTO.getAdditionalBenefitAmount());
        assertEquals(employeeDetailsDTO.getNetSalaryAmount(), mockEmployeeDetailsWithAdditionalAmountDTO.getNetSalaryAmount());
    }

    private void verifyNoAdditionalBenefitsAmount(EmployeeDetailsDTO employeeDetailsDTO) {
        assertEquals(employeeDetailsDTO.getName(), mockEmployeeDetailsWithNoAdditionalAmountDTO.getName());
        assertEquals(employeeDetailsDTO.getDesignation(), mockEmployeeDetailsWithNoAdditionalAmountDTO.getDesignation());
        assertEquals(employeeDetailsDTO.getProjectName(), mockEmployeeDetailsWithNoAdditionalAmountDTO.getProjectName());
        assertEquals(employeeDetailsDTO.getGrossSalary(), mockEmployeeDetailsWithNoAdditionalAmountDTO.getGrossSalary());
        assertEquals(employeeDetailsDTO.getTaxPercentage(), mockEmployeeDetailsWithNoAdditionalAmountDTO.getTaxPercentage());
        assertEquals(employeeDetailsDTO.getPensionPercentage(), mockEmployeeDetailsWithNoAdditionalAmountDTO.getPensionPercentage());
        assertEquals(employeeDetailsDTO.getAdditionalBenefitAmount(), mockEmployeeDetailsWithNoAdditionalAmountDTO.getAdditionalBenefitAmount());
        assertEquals(employeeDetailsDTO.getNetSalaryAmount(), mockEmployeeDetailsWithNoAdditionalAmountDTO.getNetSalaryAmount());
    }

    private EmployeeDetailsDTO createMockEmployeeDetailsDTO() {
        return EmployeeDetailsDTO.builder()
                .name(MOCK_NAME)
                .designation(MOCK_ENGINEER)
                .projectName(MOCK_PROJECT_NAME)
                .grossSalary(MOCK_GROSS_SALARY)
                .taxPercentage(MOCK_TAX_PERCENTAGE)
                .pensionPercentage(MOCK_PENSION_PERCENTAGE)
                .additionalBenefitAmount(ADDITIONAL_BENEFIT_AMOUNT)
                .netSalaryAmount(NET_SALARY_AMOUNT_WITH_ADDITIONAL_BENEFIT)
                .build();
    }
}
