package co.uk.employee.payroll.repository;

import co.uk.employee.payroll.entity.EmployeeDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeDetailsEntity, Long> {
}
