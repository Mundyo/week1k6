

package pet.store.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import pet.store.controller.model.PetStoreCustomer;
import pet.store.controller.model.PetStoreData;
import pet.store.controller.model.PetStoreEmployee;
import pet.store.service.PetStoreService;

@Slf4j
@Controller
@RestController
@RequestMapping("/pet_store")
public class PetStoreController {
    @Autowired
    private PetStoreService petStoreService;

    @PostMapping("/PetStore")
    @ResponseStatus(code = HttpStatus.CREATED)
    public PetStoreData createPetStore(
            @RequestBody PetStoreData petStoreData) {
        log.info("Creating a petstore: {}", petStoreData);
        return petStoreService.savePetStore(petStoreData);
    }

    @PutMapping("/PetStore/{petStoreId}")
    public PetStoreData updatePetStore(
            @PathVariable Long petStoreId,
            @RequestBody PetStoreData petStoreData) {
        log.info("Updating pet store with ID: {}", petStoreId);
        petStoreData.setPetStoreId(petStoreId);
        return petStoreService.savePetStore(petStoreData);
    }

    @PostMapping("/{petStoreId}/employee")
    @ResponseStatus(code = HttpStatus.CREATED)
    public PetStoreEmployee addEmployee(
            @PathVariable Long petStoreId,
            @RequestBody PetStoreEmployee employee) {
        log.info("Adding an Employee to pet store with ID: {}", petStoreId);
        return petStoreService.addEmployeeToStore(petStoreId, employee);
    }

    @PostMapping("/{petStoreId}/customer")
    @ResponseStatus(code = HttpStatus.CREATED)
    public PetStoreCustomer addCustomer(
            @PathVariable Long petStoreId,
            @RequestBody PetStoreCustomer customer) {
        log.info("Adding a customer to pet store with ID: {}", petStoreId);
        return petStoreService.addCustomerToStore(petStoreId, customer);
    }

    @GetMapping
    public List<PetStoreData> listAllPetStores() {
        log.info("Listing all pet stores");
        return petStoreService.retrieveAllPetStores();
    }
    
    
    @GetMapping("/{petStoreId}")
    public ResponseEntity<PetStoreData> getPetStoreById(
            @PathVariable Long petStoreId) {
        log.info("Retrieving pet store with ID: {}", petStoreId);
        PetStoreData petStoreData = petStoreService.retrievePetStoreById(petStoreId);
        
        if (petStoreData != null) {
            return ResponseEntity.ok(petStoreData);
        } else {
            return ResponseEntity.notFound().build();
        }
    }   
        
    @DeleteMapping("/{petStoreId}")
    public ResponseEntity<?> deletePetStoreById(
            @PathVariable Long petStoreId) {
        log.info("Deleting pet store with ID: {}", petStoreId);
        petStoreService.deletePetStoreById(petStoreId);
       
        
        String responseMessage =  "Pet store deleted successfully";
        
        
        return ResponseEntity.ok(responseMessage);
    }
}  




