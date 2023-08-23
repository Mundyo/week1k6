package pet.store.controller.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pet.store.entity.Employee;

@Data
@NoArgsConstructor
@Getter
@Setter
public class PetStoreEmployee {
    private Long employeeId;
    private String employeefirstName;
    private String employeelastName;
    private String employeejobTitle;
    private Integer employeephone;

    public PetStoreEmployee(Employee employee) {
        employeeId = employee.getEmployeeId();
        employeefirstName = employee.getEmployeefirstName();
        employeelastName = employee.getEmployeelastName();
        employeejobTitle = employee.getEmployeejobTitle();
        employeephone = employee.getEmployeephone();
    }
}
