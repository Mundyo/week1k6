package pet.store.controller.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pet.store.entity.Customer;
import pet.store.entity.PetStore;

@Data
@NoArgsConstructor
@Getter
@Setter
public class PetStoreCustomerData {
    private Long petStoreId;
    private String petStoreName;

    private Long customerId;
    private String customerFirstName;
    private String customerLastName;
    private String customerEmail;

    public PetStoreCustomerData(PetStore petStore, Customer customer) {
        petStoreId = petStore.getPetStoreId();
        petStoreName = petStore.getPetStoreName();

        customerId = customer.getCustomerId();
        customerFirstName = customer.getCustomerfirstName();
        customerLastName = customer.getCustomerlastName();
        customerEmail = customer.getCustomerEmail();
    }
}
