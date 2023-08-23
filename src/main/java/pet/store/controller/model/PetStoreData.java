package pet.store.controller.model;


import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pet.store.entity.Customer;
import pet.store.entity.Employee;
import pet.store.entity.PetStore;

@Data
@NoArgsConstructor
@Getter
@Setter
public class PetStoreData {
   

   private Long petStoreId;
   private String petStoreName;
   private String petStoreAddress;
   private String petStoreCity;
   private String petStoreState;
   private String petStoreZip;
   private String petStorePhone;
   private Set<PetStoreCustomer> customers = new HashSet<>();
   private Set<PetStoreEmployee> employees = new HashSet<>();
   
   
   public PetStoreData(PetStore petStore) {
		petStoreId = petStore.getPetStoreId();
		
		for (Customer customer : petStore.getCustomers()) {
			  customers.add(new PetStoreCustomer(customer));
		}
		
		for (Employee employee : petStore.getEmployees()) {
			  employees.add(new PetStoreEmployee(employee));
		}
	}
}