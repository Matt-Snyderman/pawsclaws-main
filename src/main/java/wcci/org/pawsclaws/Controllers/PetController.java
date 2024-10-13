package wcci.org.pawsclaws.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import wcci.org.pawsclaws.DTO.*;
import wcci.org.pawsclaws.Enums.PetType;
import wcci.org.pawsclaws.Services.PetService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;





@Controller
public class PetController {

    @Autowired
    private PetService service;

    @GetMapping({"/", "/home"})
    public String getAllPets(Model model) {
        try{
            List<PetDTO> pets = service.getAllPets();
            model.addAttribute("pets", pets);
            model.addAttribute("title", "Shelter Pets");
            return "Shelter/ViewPets";
        }
        catch(Exception ex){
            ErrorDataDTO errorData = new ErrorDataDTO();
            errorData.setErrorMessage(ex.getMessage());
            errorData.setErrorCode(500);
            model.addAttribute("errorData", errorData);
            model.addAttribute("title", "Shelter Pets - Error");
            return "Shelter/ErrorMessage";
        }
    }

    @GetMapping("details/{id}")
    public String getDetails(@PathVariable long id, Model model) {
        StatusDTO status = new StatusDTO();
        PetDTO pet = service.getPetById(id);
        pet.setStatus(pet.getStatus().replace("/n","</br>"));
        model.addAttribute("pet", pet);
        model.addAttribute("title", "Details for " + pet.getName());
        model.addAttribute("status", status);
        return "Shelter/ViewDetails";
    }

    @GetMapping("create")
    public String createPet(Model model){
        AdmissionDTO addedPet = new AdmissionDTO();
        model.addAttribute("pet", addedPet);
        model.addAttribute("title", "Create pet " );
        model.addAttribute("petTypes", PetType.values());
        return "Shelter/CreatePet";
    }
    
    @PostMapping ("saveadd")
    public String saveadd(@ModelAttribute AdmissionDTO pet, Model model){
        try{
            service.saveAdd(pet);
        }
        catch (Exception ex){
            pet.setErrorMessage(ex.getMessage());
            pet.setErrorCode(400);
            model.addAttribute("pet", pet);
            model.addAttribute("title", "Create pet " );
            model.addAttribute("petTypes", PetType.values());
            return "Shelter/CreatePet";
        }
        return "redirect:/home";
    }

    @GetMapping("edit/{id}")
    public String editPet(@PathVariable long id, Model model){
        EditPetDTO pet = new EditPetDTO(service.getPetById(id));
        model.addAttribute("pet", pet);
        model.addAttribute("title", "Edit pet " );
        model.addAttribute("petTypes", PetType.values());
        return "Shelter/EditPet";
    }

    @PutMapping ("saveEdit")
    public String saveEdit(@ModelAttribute EditPetDTO pet, Model model){
        try{
            service.saveEdit(pet);
        }
        catch (Exception ex){
            pet.setErrorMessage(ex.getMessage());
            pet.setErrorCode(400);
            model.addAttribute("pet", pet);
            model.addAttribute("title", "Create pet " );
            model.addAttribute("petTypes", PetType.values());
            return "Shelter/EditPet";
        }
        return "redirect:/home";
    }

    @GetMapping("feed/{id}")
    public String feedPet(@PathVariable long id, Model model){
        StatusDTO status = service.carePet(id, "feed");
        PetDTO pet = service.getPetById(id);
        model.addAttribute("pet", pet);
        model.addAttribute("title", "Details for " + pet.getName());
        model.addAttribute("petTypes", PetType.values());
        model.addAttribute("status", status);
        return "Shelter/ViewDetails";
    }

    @GetMapping("water/{id}")
    public String waterPet(@PathVariable long id, Model model){
        StatusDTO status = service.carePet(id, "water");
        PetDTO pet = service.getPetById(id);
        model.addAttribute("pet", pet);
        model.addAttribute("title", "Details for " + pet.getName());
        model.addAttribute("petTypes", PetType.values());
        model.addAttribute("status", status);
        return "Shelter/ViewDetails";
    }

    @GetMapping("play/{id}")
    public String playPet(@PathVariable long id, Model model){
        StatusDTO status = service.carePet(id, "play");
        PetDTO pet = service.getPetById(id);
        model.addAttribute("pet", pet);
        model.addAttribute("title", "Details for " + pet.getName());
        model.addAttribute("petTypes", PetType.values());
        model.addAttribute("status", status);
        return "Shelter/ViewDetails";
    }

    @GetMapping("heal/{id}")
    public String healPet(@PathVariable long id, Model model){
        StatusDTO status = service.carePet(id, "heal");
        PetDTO pet = service.getPetById(id);
        model.addAttribute("pet", pet);
        model.addAttribute("title", "Details for " + pet.getName());
        model.addAttribute("petTypes", PetType.values());
        model.addAttribute("status", status);
        return "Shelter/ViewDetails";
    }

    // @GetMapping("adopt/{id}")
    // public String adoptPet(@PathVariable long id, Model model) {
    //     StatusDTO status = new StatusDTO();
    //     PetDTO pet = service.getPetById(id);
    //     model.addAttribute("pet", pet);
    //     model.addAttribute("title", "Details for " + pet.getName());
    //     model.addAttribute("status", status);
    //     return "Shelter/ViewDetails";
    // }


    @GetMapping("adopt/{id}")
    public String adoptPet(@PathVariable long id, Model model) {
       try{
        service.deletePetById(id);
       }
       catch (Exception ex){
        ErrorDataDTO errorData = new ErrorDataDTO();
        errorData.setErrorMessage(ex.getMessage());
        errorData.setErrorCode(500);
        model.addAttribute("errorData", errorData);
        model.addAttribute("title", "Error adding pet ");
        return "Shelter/ErrorMessage";
       }
       return "redirect:/home";
    }
}

