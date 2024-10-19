package wcci.org.pawsclaws.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import wcci.org.pawsclaws.DTO.*;
import wcci.org.pawsclaws.Enums.PetType;
import wcci.org.pawsclaws.Services.PetService;
import org.springframework.web.bind.annotation.*;

@Controller
public class PetController {

    @Autowired
    private PetService service; // Injecting the PetService to handle business logic

    /**
     * Handles GET requests to "/" and "/home" and returns a view displaying all pets.
     * @param model The model to pass attributes to the view
     * @return The name of the view to render the list of pets
     */
    @GetMapping({"/", "/home"})
    public String getAllPets(Model model) {
        try {
            List<PetDTO> pets = service.getAllPets(); // Fetching all pets from the service
            model.addAttribute("pets", pets); // Adding the list of pets to the model
            model.addAttribute("title", "Shelter Pets"); // Setting the title for the view
            return "Shelter/ViewPets"; // Returning the view template for listing pets
        } catch (Exception ex) {
            ErrorDataDTO errorData = new ErrorDataDTO();
            errorData.setErrorMessage(ex.getMessage()); // Setting error message if exception occurs
            errorData.setErrorCode(500); // Setting error code
            model.addAttribute("errorData", errorData); // Adding error data to the model
            model.addAttribute("title", "Shelter Pets - Error");
            return "Shelter/ErrorMessage"; // Returning the error view
        }
    }

    /**
     * Displays details of a specific pet.
     * @param id The ID of the pet to display
     * @param model The model to pass attributes to the view
     * @return The name of the view to display pet details
     */
    @GetMapping("details/{id}")
    public String getDetails(@PathVariable long id, Model model) {
        StatusDTO status = new StatusDTO(); // Initialize a StatusDTO object
        PetDTO pet = service.getPetById(id); // Fetch pet details using the provided ID
        pet.setStatus(pet.getStatus().replace("/n", "</br>")); // Replace newline characters for HTML
        model.addAttribute("pet", pet); // Add pet details to the model
        model.addAttribute("title", "Details for " + pet.getName()); // Set page title
        model.addAttribute("status", status); // Add status to the model
        return "Shelter/ViewDetails"; // Return the details view
    }

    /**
     * Displays the form to create a new pet.
     * @param model The model to pass attributes to the view
     * @return The name of the view to display the pet creation form
     */
    @GetMapping("create")
    public String createPet(Model model) {
        AdmissionDTO addedPet = new AdmissionDTO(); // Create a new AdmissionDTO object
        model.addAttribute("pet", addedPet); // Add the AdmissionDTO to the model
        model.addAttribute("title", "Create pet"); // Set the page title
        model.addAttribute("petTypes", PetType.values()); // Add pet types to the model for selection
        return "Shelter/CreatePet"; // Return the pet creation view
    }

    /**
     * Handles form submission for creating a new pet.
     * @param pet The AdmissionDTO object containing pet data
     * @param model The model to pass attributes to the view
     * @return Redirects to the home page on success, or returns the pet creation form on error
     */
    @PostMapping("saveadd")
    public String saveadd(@ModelAttribute AdmissionDTO pet, Model model) {
        try {
            service.saveAdd(pet); // Save the new pet using the service
        } catch (Exception ex) {
            pet.setErrorMessage(ex.getMessage()); // Set error message on exception
            pet.setErrorCode(400); // Set error code
            model.addAttribute("pet", pet); // Add pet data back to the model for the form
            model.addAttribute("title", "Create pet "); // Set page title
            model.addAttribute("petTypes", PetType.values()); // Add pet types to the model
            return "Shelter/CreatePet"; // Return the pet creation view with error info
        }
        return "redirect:/home"; // Redirect to the home page on success
    }

    /**
     * Displays the form to edit an existing pet.
     * @param id The ID of the pet to edit
     * @param model The model to pass attributes to the view
     * @return The name of the view to display the pet edit form
     */
    @GetMapping("edit/{id}")
    public String editPet(@PathVariable long id, Model model) {
        EditPetDTO pet = new EditPetDTO(service.getPetById(id)); // Create an EditPetDTO from the pet's data
        model.addAttribute("pet", pet); // Add the EditPetDTO to the model
        model.addAttribute("title", "Edit pet "); // Set the page title
        model.addAttribute("petTypes", PetType.values()); // Add pet types to the model
        return "Shelter/EditPet"; // Return the pet edit view
    }

    /**
     * Handles form submission for editing a pet.
     * @param pet The EditPetDTO object containing updated pet data
     * @param model The model to pass attributes to the view
     * @return Redirects to the home page on success, or returns the edit form on error
     */
    @PutMapping("saveEdit")
    public String saveEdit(@ModelAttribute EditPetDTO pet, Model model) {
        try {
            service.saveEdit(pet); // Save the edited pet using the service
        } catch (Exception ex) {
            pet.setErrorMessage(ex.getMessage()); // Set error message on exception
            pet.setErrorCode(400); // Set error code
            model.addAttribute("pet", pet); // Add pet data back to the model for the form
            model.addAttribute("title", "Create pet "); // Set page title
            model.addAttribute("petTypes", PetType.values()); // Add pet types to the model
            return "Shelter/EditPet"; // Return the pet edit view with error info
        }
        return "redirect:/home"; // Redirect to the home page on success
    }

    /**
     * Performs a 'feed' action for a specific pet.
     * @param id The ID of the pet to feed
     * @param model The model to pass attributes to the view
     * @return The name of the view to display the pet details after feeding
     */
    @GetMapping("feed/{id}")
    public String feedPet(@PathVariable long id, Model model) {
        StatusDTO status = service.carePet(id, "feed"); // Perform the feed action and get status
        PetDTO pet = service.getPetById(id); // Fetch pet details
        model.addAttribute("pet", pet); // Add pet details to the model
        model.addAttribute("title", "Details for " + pet.getName()); // Set page title
        model.addAttribute("petTypes", PetType.values()); // Add pet types to the model
        model.addAttribute("status", status); // Add status to the model
        return "Shelter/ViewDetails"; // Return the details view
    }

    /**
     * Performs a 'water' action for a specific pet.
     * @param id The ID of the pet to water
     * @param model The model to pass attributes to the view
     * @return The name of the view to display the pet details after watering
     */
    @GetMapping("water/{id}")
    public String waterPet(@PathVariable long id, Model model) {
        StatusDTO status = service.carePet(id, "water"); // Perform the water action and get status
        PetDTO pet = service.getPetById(id); // Fetch pet details
        model.addAttribute("pet", pet); // Add pet details to the model
        model.addAttribute("title", "Details for " + pet.getName()); // Set page title
        model.addAttribute("petTypes", PetType.values()); // Add pet types to the model
        model.addAttribute("status", status); // Add status to the model
        return "Shelter/ViewDetails"; // Return the details view
    }

    /**
     * Performs a 'play' action for a specific pet.
     * @param id The ID of the pet to play with
     * @param model The model to pass attributes to the view
     * @return The name of the view to display the pet details after playing
     */
    @GetMapping("play/{id}")
    public String playPet(@PathVariable long id, Model model) {
        
        try{
            StatusDTO status = service.carePet(id, "play"); // Perform the play action and get status
        PetDTO pet = service.getPetById(id); // Fetch pet details
        model.addAttribute("pet", pet); // Add pet details to the model
        model.addAttribute("title", "Details for " + pet.getName()); // Set page title
        model.addAttribute("petTypes", PetType.values()); // Add pet types to the model
        model.addAttribute("status", status); // Add status to the model
        return "Shelter/ViewDetails"; // Return the details view
    }
    catch (Exception ex){
        ErrorDataDTO errorDataDTO = new ErrorDataDTO();
        errorDataDTO.setErrorMessage(ex.getMessage());
        errorDataDTO.setErrorCode(200);
        model.addAttribute("errorDataDTO", errorDataDTO);
        return "Shelter/errorMessage";
    }
}

    /**
     * Performs a 'heal' action for a specific pet.
     * @param id The ID of the pet to heal
     * @param model The model to pass attributes to the view
     * @return The name of the view to display the pet details after healing
     */
    @GetMapping("heal/{id}")
    public String healPet(@PathVariable long id, Model model) {
        try{
        StatusDTO status = service.carePet(id, "heal"); // Perform the heal action and get status
        PetDTO pet = service.getPetById(id); // Fetch pet details
        model.addAttribute("pet", pet); // Add pet details to the model
        model.addAttribute("title", "Details for " + pet.getName()); // Set page title
        model.addAttribute("petTypes", PetType.values()); // Add pet types to the model
        model.addAttribute("status", status); // Add status to the model
        return "Shelter/ViewDetails"; // Return the details view
    }
    catch (Exception ex){
        ErrorDataDTO errorDataDTO = new ErrorDataDTO();
        errorDataDTO.setErrorMessage(ex.getMessage());
        errorDataDTO.setErrorCode(200);
        model.addAttribute("errorDataDTO", errorDataDTO);
        return "Shelter/errorMessage";
    }
}

    /**
     * Deletes a pet, marking it as adopted.
     * @param id The ID of the pet to adopt
     * @param model The model to pass attributes to the view
     * @return Redirects to the home page on success, or displays an error page on failure
     */
    @GetMapping("adopt/{id}")
    public String adoptPet(@PathVariable long id, Model model) {
        try {
            service.deletePetById(id); // Delete the pet by its ID
        } catch (Exception ex) {
            ErrorDataDTO errorData = new ErrorDataDTO();
            errorData.setErrorMessage(ex.getMessage()); // Set error message on exception
            errorData.setErrorCode(500); // Set error code
            model.addAttribute("errorData", errorData); // Add error data to the model
            model.addAttribute("title", "Error adopting pet");
            return "Shelter/ErrorMessage"; // Return the error view
        }
        return "redirect:/home"; // Redirect to the home page on success
    }
}
