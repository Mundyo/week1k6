
package pet.store.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pet.store.controller.model.PetStoreCustomer;
import pet.store.controller.model.PetStoreCustomerData;
import pet.store.controller.model.PetStoreData;
import pet.store.controller.model.PetStoreEmployee;
import pet.store.dao.CustomerDao;
import pet.store.dao.EmployeeDao;
import pet.store.dao.PetStoreDao;
import pet.store.entity.Customer;
import pet.store.entity.Employee;
import pet.store.entity.PetStore;

@Service
public class PetStoreService {
    @Autowired
    private PetStoreDao petStoreDao;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private EmployeeDao employeeDao;

    @Transactional(readOnly = false)
    public PetStoreData savePetStore(PetStoreData petStoreData) {
        Long petStoreId = petStoreData.getPetStoreId();
        PetStore petStore = findOrCreatePetStore(petStoreId);
        copyPetStoreFields(petStore, petStoreData);
        return new PetStoreData(petStoreDao.save(petStore));
    }

    private void copyPetStoreFields(PetStore petStore, PetStoreData petStoreData) {
        petStore.setPetStoreName(petStoreData.getPetStoreName());
        petStore.setPetStoreAddress(petStoreData.getPetStoreAddress());
        petStore.setPetStoreCity(petStoreData.getPetStoreCity());
        petStore.setPetStoreState(petStoreData.getPetStoreState());
        petStore.setPetStoreZip(petStoreData.getPetStoreZip());
        petStore.setPetStorePhone(petStoreData.getPetStorePhone());
    }

    private PetStore findOrCreatePetStore(Long petStoreId) {
        PetStore petStore;
        if (Objects.isNull(petStoreId)) {
            petStore = new PetStore();
        } else {
            petStore = findPetStoreById(petStoreId);
        }
        return petStore;
    }

    private PetStore findPetStoreById(Long petStoreId) {
        return petStoreDao.findById(petStoreId)
                .orElseThrow(() -> new NoSuchElementException(
                        "petStore with ID=" + petStoreId + " was not found."));
    }

    @Transactional(readOnly = false)
    public PetStoreEmployee saveEmployee(Long petStoreId, PetStoreEmployee petStoreEmployee) {
        PetStore petStore = findPetStoreById(petStoreId);
        Employee employee = findOrCreateEmployee(petStoreId, petStoreEmployee.getEmployeeId());

        copyEmployeeFields(employee, petStoreEmployee);

        employee.setPetStore(petStore);
        petStore.getEmployees().add(employee);

        Employee dbEmployee = employeeDao.save(employee);
        
        return new PetStoreEmployee(dbEmployee);
    }

    private Employee findOrCreateEmployee(Long petStoreId, Long employeeId) {
        if (employeeId == null) {
            return new Employee();
        } else {
            return findEmployeeById(petStoreId, employeeId);
        }
    }

    private Employee findEmployeeById(Long petStoreId, Long employeeId) {
        Employee employee = employeeDao.findById(employeeId)
                .orElseThrow(() -> new NoSuchElementException(
                        "Employee with ID=" + employeeId + " was not found."));

        if (employee.getPetStore().getPetStoreId().equals(petStoreId)) {
            return employee;
        } else {
            throw new IllegalArgumentException("Employee with ID=" + employeeId +
                    " does not belong to pet store with ID=" + petStoreId);
        }
    }

    private void copyEmployeeFields(Employee employee, PetStoreEmployee petStoreEmployee) {
        employee.setEmployeefirstName(petStoreEmployee.getEmployeefirstName());
        employee.setEmployeelastName(petStoreEmployee.getEmployeelastName());
        employee.setEmployeejobTitle(petStoreEmployee.getEmployeejobTitle());
        employee.setEmployeephone(petStoreEmployee.getEmployeephone());
    }

    
    
    
   
    @Transactional(readOnly = false)
    public PetStoreCustomerData saveCustomer(Long petStoreId, PetStoreCustomer petStoreCustomer) {
        PetStore petStore = findPetStoreById(petStoreId);
        Customer customer = findOrCreateCustomer(petStoreId, petStoreCustomer.getCustomerId());

        copyCustomerFields(customer, petStoreCustomer);

        customer.getPetStores().add(petStore);
        petStore.getCustomers().add(customer);

        Customer dbCustomer = customerDao.save(customer);
        
        return new PetStoreCustomerData(petStore, dbCustomer);
    }


    
    
    
    private Customer findOrCreateCustomer(Long petStoreId, Long customerId) {
        if (customerId == null) {
            return new Customer();
        } else {
            return findCustomerById(petStoreId, customerId);
        }
    }

    
   private void copyCustomerFields(Customer customer, PetStoreCustomer petStoreCustomer) {
        customer.setCustomerfirstName(petStoreCustomer.getCustomerfirstName());
        customer.setCustomerlastName(petStoreCustomer.getCustomerlastName());
        customer.setCustomerEmail(petStoreCustomer.getCustomerEmail());
    }
    
    
    
    @Transactional(readOnly = false)
	public PetStoreEmployee addEmployeeToStore(Long petStoreId, PetStoreEmployee employee) {
	
		PetStore petStore = findPetStoreById(petStoreId);
//		Employee petStoreEmployee;
		Employee newEmployee = findOrCreateEmployee(petStoreId, employee.getEmployeeId());
		
//		Employee newEmployee;
		copyEmployeeFields(newEmployee, employee);

        newEmployee.setPetStore(petStore);
        petStore.getEmployees().add(newEmployee);

        Employee dbEmployee = employeeDao.save(newEmployee);
        return new PetStoreEmployee(dbEmployee);
	}
    
    
    

    @Transactional(readOnly = false)
    public PetStoreCustomer addCustomerToStore(Long petStoreId, PetStoreCustomer customer) {
        PetStore petStore = findPetStoreById(petStoreId);

        Customer newCustomer = findOrCreateCustomer(petStoreId, customer.getCustomerId());

        newCustomer.getPetStores().add(petStore);
        petStore.getCustomers().add(newCustomer);

        Customer dbCustomer = customerDao.save(newCustomer);

        return new PetStoreCustomer(dbCustomer);
    }

    
    
    
    
    @Transactional
	public List<PetStoreData> retrieveAllPetStores() {
		
    	List<PetStore> allPetStores = petStoreDao.findAll();
        List<PetStoreData> summaryList = new ArrayList<>();

        for (PetStore petStore : allPetStores) {
            PetStoreData summary = new PetStoreData();
            summary.setPetStoreId(petStore.getPetStoreId());
            summary.setPetStoreName(petStore.getPetStoreName());
            summary.setPetStoreAddress(petStore.getPetStoreAddress());
            summary.setPetStoreCity(petStore.getPetStoreCity());
            summary.setPetStoreState(petStore.getPetStoreState());
            summary.setPetStoreZip(petStore.getPetStoreZip());
            summary.setPetStorePhone(petStore.getPetStorePhone());

            summaryList.add(summary);
        }

        return summaryList;
	}
    
    
    @Transactional
	public void deletePetStoreById(Long petStoreId) {
		PetStore petStore = findPetStoreById(petStoreId);

        // Remove employees associated with the pet store
        petStore.getEmployees().clear();

        // Remove customer join table records for the pet store
        for (Customer customer : petStore.getCustomers()) {
            customer.getPetStores().remove(petStore);
        }

        // Finally, delete the pet store itself
        petStoreDao.delete(petStore);
    }
    
    
    @Transactional
	public PetStoreData retrievePetStoreById(Long petStoreId) {
		
		  Optional<PetStore> petStoreOptional = petStoreDao.findById(petStoreId);
	        
	        if (petStoreOptional.isPresent()) {
	            PetStore petStore = petStoreOptional.get();
	            return new PetStoreData(petStore);
	        } else {
	            return null;
	        }
    }
    
    
    private Customer findCustomerById(Long petStoreId, Long customerId) {
        Customer customer = customerDao.findById(customerId).orElseThrow(() -> new NoSuchElementException(
                 "Customer with ID=" + customerId + " was not found."));

        if (customer.getPetStores().stream().anyMatch(store -> store.getPetStoreId().equals(petStoreId))) {
            return customer;
        } else {
            throw new IllegalArgumentException("Customer with ID=" + customerId +
                    " does not belong to pet store with ID=" + petStoreId);
        }
    }
   
		
	}


        
