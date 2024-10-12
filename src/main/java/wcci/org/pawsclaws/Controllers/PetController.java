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
import org.springframework.web.bind.annotation.RequestParam;




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
        PetDTO pet = service.getPetById(id);
        pet.setStatus(pet.getStatus().replace("/n","</br>"));
        model.addAttribute("pet", pet);
        model.addAttribute("title", "Details for " + pet.getName());
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

    

}
