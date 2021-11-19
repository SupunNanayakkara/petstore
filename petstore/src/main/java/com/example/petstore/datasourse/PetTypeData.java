package com.example.petstore.datasourse;

import com.example.petstore.PetType;

import java.util.ArrayList;
import java.util.List;

public class PetTypeData {
    private List<PetType> petTypeList = new ArrayList<PetType>();
    private static PetTypeData obj = new PetTypeData();

    private  PetTypeData() {
        this.addPetType(new PetType(1, "Dog"));
        this.addPetType(new PetType(2, "Cat"));
        this.addPetType(new PetType(3, "Bird"));
    }

    public static PetTypeData getInstance() {
        return obj;
    }

    public PetType addPetType(PetType petTypeObj) {
        Integer petTypeId = petTypeList.size() + 1;
        petTypeObj.setPetTypeId(petTypeId);
        petTypeList.add(petTypeObj);
        return petTypeObj;
    }

    public List<PetType> getPetTypeList() {
        return petTypeList;
    }

    public PetType getPetTypeById(Integer id) {
        for (PetType petType : petTypeList) {
            if (petType.getPetTypeId().equals(id)) {
                return petType;
            }
        }
        return null;
    }

    public PetType getPetTypeByName(String name) {
        for (PetType petType : petTypeList) {
            if (petType.getPetTypeName().equals(name)) {
                return petType;
            }
        }
        return null;
    }

    public boolean petTypeExists(Integer id) {
        for (PetType petType : petTypeList) {
            if (petType.getPetTypeId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public PetType updatePetType(PetType toBeUpdated, Integer petId) {
        for (PetType pet : petTypeList) {
            if (pet.getPetTypeId().equals(petId)) {
                if(toBeUpdated.getPetTypeName() != null){
                    pet.setPetTypeName(toBeUpdated.getPetTypeName());
                }
                return pet;
            }
        }
        return null;
    }

    public boolean deletePetType(Integer id) {
        for (PetType petType : petTypeList) {
            if (petType.getPetTypeId().equals(id)) {
                petTypeList.remove(petType);
                return true;
            }
        }
        return false;
    }
}
