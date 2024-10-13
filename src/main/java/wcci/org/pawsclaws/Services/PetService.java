package wcci.org.pawsclaws.Services;


import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import wcci.org.pawsclaws.DTO.AdmissionDTO;
import wcci.org.pawsclaws.DTO.AdoptDTO;
import wcci.org.pawsclaws.DTO.EditPetDTO;
import wcci.org.pawsclaws.DTO.PetDTO;
import wcci.org.pawsclaws.DTO.StatusDTO;

@Service
public class PetService {
    private final RestTemplate restTemplate;
    public final String server = "http://localhost:8080";

    public PetService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<PetDTO> getAllPets() {
        String url = server + "/api/v1/shelter";

        PetDTO[] pets = restTemplate.getForObject(url, PetDTO[].class);

        return Arrays.asList(pets);
    }

    public PetDTO getPetById(long id) {

        String url = server + "/api/v1/shelter/" + id;

        PetDTO pet = restTemplate.getForObject(url, PetDTO.class);

        return pet;
    }

    // public PetDTO adoptPet(long id){
    //     String url = server + "/api/v1/shelter/" + id;

    //     PetDTO pet = restTemplate.getForObject(url, PetDTO.class);

    //     return PetDTO.delete(pet);

    // }

    

    public PetDTO saveAdd (AdmissionDTO admit){
        String url = server + "/api/v1/shelter";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<AdmissionDTO> requestEntity= new HttpEntity<>(admit, headers);
        PetDTO pet = restTemplate.postForObject(url, requestEntity, PetDTO.class);

        return pet;
    }

    public PetDTO saveEdit (EditPetDTO edit){
        String url = server + "/api/v1/shelter";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<EditPetDTO> requestEntity= new HttpEntity<>(edit, headers);
        PetDTO pet = restTemplate.patchForObject(url, requestEntity, PetDTO.class);

        return pet;
    }

    public StatusDTO carePet (long id, String action){
        String url = server + "/api/v1/shelter/" + action + "/" + id;
        StatusDTO status = restTemplate.getForObject(url,  StatusDTO.class);

        return status;
    }

   public void deletePetById (long id){
    String url = server + "/api/v1/shelter/" + id;
    restTemplate.delete(url);
   }
    

}
