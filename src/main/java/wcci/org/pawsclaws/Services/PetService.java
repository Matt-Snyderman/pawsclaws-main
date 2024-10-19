package wcci.org.pawsclaws.Services;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import wcci.org.pawsclaws.DTO.AdmissionDTO;
import wcci.org.pawsclaws.DTO.EditPetDTO;
import wcci.org.pawsclaws.DTO.PetDTO;
import wcci.org.pawsclaws.DTO.StatusDTO;

@Service
public class PetService {

    private final RestTemplate restTemplate; // Used to make REST API calls
    public final String server = "http://localhost:8080"; // Base URL of the server

    // Constructor to initialize RestTemplate
    public PetService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Fetches the list of all pets from the shelter API.
     * @return A list of PetDTO objects representing all pets.
     */
    public List<PetDTO> getAllPets() {
        String url = server + "/api/v1/shelter"; // API endpoint to get all pets

        // Makes GET request to retrieve the array of pets
        PetDTO[] pets = restTemplate.getForObject(url, PetDTO[].class);

        // Converts array to a list and returns it
        return Arrays.asList(pets);
    }

    /**
     * Fetches details of a pet by its ID from the shelter API.
     * @param id The ID of the pet to retrieve.
     * @return A PetDTO object representing the pet's details.
     */
    public PetDTO getPetById(long id) {
        String url = server + "/api/v1/shelter/" + id; // API endpoint for a specific pet by ID

        // Makes GET request to retrieve pet details
        PetDTO pet = restTemplate.getForObject(url, PetDTO.class);

        return pet;
    }

    /**
     * Sends a request to add a new pet to the shelter.
     * @param admit The AdmissionDTO object containing pet information to be added.
     * @return A PetDTO object representing the newly added pet.
     */
    public PetDTO saveAdd(AdmissionDTO admit) {
        String url = server + "/api/v1/shelter"; // API endpoint for adding a new pet
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json"); // Set headers for JSON content
        HttpEntity<AdmissionDTO> requestEntity = new HttpEntity<>(admit, headers); // Wrap AdmissionDTO in an HttpEntity
        
        // Makes POST request to add a new pet and receive the created pet object
        PetDTO pet = restTemplate.postForObject(url, requestEntity, PetDTO.class);

        return pet;
    }

    /**
     * Sends a request to update an existing pet's details.
     * @param edit The EditPetDTO object containing updated pet information.
     * @return A PetDTO object representing the updated pet.
     */
    public PetDTO saveEdit(EditPetDTO edit) {
        String url = server + "/api/v1/shelter"; // API endpoint for updating a pet
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json"); // Set headers for JSON content
        HttpEntity<EditPetDTO> requestEntity = new HttpEntity<>(edit, headers); // Wrap EditPetDTO in an HttpEntity
        
        // Makes PATCH request to update the pet details
        PetDTO pet = restTemplate.patchForObject(url, requestEntity, PetDTO.class);

        return pet;
    }

    /**
     * Performs an action (like feed, water, play, heal) on a specific pet.
     * @param id The ID of the pet to perform the action on.
     * @param action The action to be performed (feed, water, play, heal).
     * @return A StatusDTO object representing the status of the pet after the action.
     */
    public StatusDTO carePet(long id, String action) {
        String url = server + "/api/v1/shelter/" + action + "/" + id; // API endpoint for the specific action on the pet
        
        // Makes GET request to perform the action and retrieve the status
        StatusDTO status = restTemplate.getForObject(url, StatusDTO.class);

        return status;
    }

    /**
     * Deletes a pet by its ID, effectively marking it as adopted or removed.
     * @param id The ID of the pet to delete.
     */
    public void deletePetById(long id) {
        String url = server + "/api/v1/shelter/" + id; // API endpoint to delete the pet by ID
        
        // Makes DELETE request to remove the pet
        restTemplate.delete(url);
    }
}