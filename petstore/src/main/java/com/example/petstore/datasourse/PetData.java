package com.example.petstore.datasourse;

import com.example.petstore.Pet;
import com.example.petstore.PetType;

import java.util.ArrayList;
import java.util.List;

public class PetData {
    private List<Pet> petList = new ArrayList<Pet>();
    private static PetData obj = new PetData();

    private  PetData() {
        this.addPet(new Pet(1, new PetType(1, "Dog"), "Boola", 3));
        this.addPet(new Pet(2, new PetType(2, "Cat"), "Sudda", 1));
        this.addPet(new Pet(3, new PetType(3, "Bird"), "Peththappu", 4));
        this.addPet(new Pet(4, new PetType(1, "Dog"), "Scooby Doo", 7));
    }

    public static PetData getInstance() {
        return obj;
    }

    public Pet addPet(Pet petObj) {
        Integer petId = petList.size() + 1;
        petObj.setPetId(petId);
        petList.add(petObj);
        return petObj;
    }

    public List<Pet> getPetList() {
        return petList;
    }

    public Pet getPetById(Integer id) {
        for (Pet pet : petList) {
            if (pet.getPetId().equals(id)) {
                return pet;
            }
        }
        return null;
    }

    public Pet getPetByName(String name) {
        for (Pet pet : petList) {
            if (pet.getPetName().equals(name)) {
                return pet;
            }
        }
        return null;
    }

    public Pet getPetByAge(Integer age) {
        for (Pet pet : petList) {
            if (pet.getPetAge().equals(age)) {
                return pet;
            }
        }
        return null;
    }

    public List<Pet> getPetByType(String type) {
        List<Pet> petListByType = new ArrayList<Pet>();
        for (Pet pet : petList) {
            if (pet.getPetType().getPetTypeName().equals(type)) {
                petListByType.add(pet);
            }
        }
        return petListByType;
    }

    public Pet updatePet(Pet toBeUpdated, Integer petId) {
        for (Pet pet : petList) {
            if (pet.getPetId().equals(petId)) {
                if(toBeUpdated.getPetAge() != null){
                    pet.setPetAge(toBeUpdated.getPetAge());
                }
                if(toBeUpdated.getPetName() != null){
                    pet.setPetName(toBeUpdated.getPetName());
                }
                if(toBeUpdated.getPetType() != null){
                    pet.setPetType(toBeUpdated.getPetType());
                }
                return pet;
            }
        }
        return null;
    }

    public boolean petExists(Integer id) {
        for (Pet pet : petList) {
            if (pet.getPetId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public boolean deletePet(Integer id) {
        for (Pet pet : petList) {
            if (pet.getPetId().equals(id)) {
                petList.remove(pet);
                return true;
            }
        }
        return false;
    }
}
