package wcci.org.pawsclaws.Services;


import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import wcci.org.pawsclaws.DTO.AdmissionDTO;
import wcci.org.pawsclaws.DTO.PetDTO;

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

    public PetDTO saveAdd (AdmissionDTO admit){
        String url = server + "/api/v1/shelter";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<AdmissionDTO> requestEntity= new HttpEntity<>(admit, headers);
        PetDTO pet = restTemplate.postForObject(url, requestEntity, PetDTO.class);

        return pet;
    }
}
