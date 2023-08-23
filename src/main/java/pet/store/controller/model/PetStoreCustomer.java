package pet.store.controller.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pet.store.entity.Customer;

@Data
@NoArgsConstructor
@Getter
@Setter
public class PetStoreCustomer {
    private Long customerId;
    private String customerfirstName;
    private String customerlastName;
    private String customerEmail;

    public PetStoreCustomer(Customer customer) {
        customerId = customer.getCustomerId();
        customerfirstName = customer.getCustomerfirstName();
        customerlastName = customer.getCustomerlastName();
        customerEmail = customer.getCustomerEmail();
    }
}
