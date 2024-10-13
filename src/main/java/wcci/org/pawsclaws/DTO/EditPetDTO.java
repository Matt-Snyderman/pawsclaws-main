package wcci.org.pawsclaws.DTO;

public class EditPetDTO extends ErrorDataDTO{
    private long id;
    private String name;
    private Integer age;

    public EditPetDTO() {
        super();
    }

    public EditPetDTO(PetDTO pet) {
        id = pet.getId();
        name = pet.getName();
        age = pet.getAge();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

}
