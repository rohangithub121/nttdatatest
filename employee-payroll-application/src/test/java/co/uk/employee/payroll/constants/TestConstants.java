package co.uk.employee.payroll.constants;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class TestConstants {
    public static final long MOCK_EMP_ID = 123L;
    public static final int MOCK_EMP_ID_NOT_PRESENT = 456;
    public static final String ADDITIONAL_BENEFIT_AMOUNT = "100.00";
    public static final BigDecimal NET_SALARY_AMOUNT_WITH_ADDITIONAL_BENEFIT = BigDecimal.valueOf(7100.00).setScale(2, RoundingMode.HALF_UP);
    public static final String MOCK_NAME = "john";
    public static final String MOCK_ENGINEER = "engineer";
    public static final String MOCK_PROJECT_NAME = "sample";
    public static final String MOCK_GROSS_SALARY = "10000.00";
    public static final String MOCK_TAX_PERCENTAGE = "20.00";
    public static final String MOCK_PENSION_PERCENTAGE = "10.00";
    public static final BigDecimal MOCK_NET_SALARY_AMOUNT_WITH_NO_ADDITIONAL_BENEFITS = BigDecimal.valueOf(7000.00).setScale(2, RoundingMode.HALF_UP);
    public static final String POST_EMPLOYEE_PAYLOAD_PATH = "/employee/payroll";
    public static final String GET_EMPLOYEE_DETAILS_URL = "/employee/payroll/employees-detail/123";
    public static final String ZERO = "0.00";

    private TestConstants() {

    }
}
