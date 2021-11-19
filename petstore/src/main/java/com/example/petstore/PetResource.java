package com.example.petstore;

import java.util.List;
import java.util.ArrayList;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.example.petstore.datasourse.PetData;
import com.example.petstore.datasourse.PetTypeData;

import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

@Path("/v1/pets")
@Produces("application/json")
public class PetResource {
	private PetData petList = PetData.getInstance();
	private PetTypeData petTypeList = PetTypeData.getInstance();

	//retrieve all the pets from petData
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "All Pets", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))) })
	@GET
	public Response getPets() {
		List<Pet> pets = petList.getPetList();
		return Response.ok(pets).build();
	}

	//pet search by petId
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Search pet by id", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))),
			@APIResponse(responseCode = "404", description = "No pet found for this id.") })
	@GET
	@Path("{petId}")
	public Response getPet(@PathParam("petId") Integer petId) {
		Pet pet = petList.getPetById(petId);
		if (pet == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(pet).build();
	}

	//pet search by name
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Search pet by name", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))),
			@APIResponse(responseCode = "404", description = "No pet found under this name.") })
	@GET
	@Path("/searchByName/{petName}")
	public Response getPetByName(@PathParam("petName") String petName) {
		Pet pet = petList.getPetByName(petName);
		if(pet == null){
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(pet).build();
	}

	//pet search by age
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Search pet by pet type", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))),
			@APIResponse(responseCode = "404", description = "No pet found under this type.") })
	@GET
	@Path("/searchByAge/{petAge}")
	public Response getPetByAge(@PathParam("petAge") Integer petAge) {
		Pet pet = petList.getPetByAge(petAge);
		if(pet == null){
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(pet).build();
	}

	//pet search by pet type
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Search pet by age", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))),
			@APIResponse(responseCode = "404", description = "No pet found for this age.") })
	@GET
	@Path("/searchByType/{petType}")
	public Response getPetByType(@PathParam("petType") String petType) {
		List<Pet> pets = petList.getPetByType(petType);
		if(pets == null){
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(pets).build();
	}

	//add new pet to the petData
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Added New Pet", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))),
			@APIResponse(responseCode = "404", description = "Error while adding") })
	@POST
	@Path("/new")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addPet(Pet petObj) {
		petObj.setPetType(petTypeList.getPetTypeById(petObj.getPetTypeId()));
		//petObj.setPetTypeId(null);
		Pet newPet = petList.addPet(petObj);
		return Response.ok(newPet).build();
	}

	//update existing pet
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Updated pet", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))),
			@APIResponse(responseCode = "404", description = "No Pet found for this id.") })
	@PUT
	@Path("/edit/{petId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updatePet(Pet updatePetObj, @PathParam("petId") Integer petId) {
		if(!petList.petExists(petId)){
			return Response.status(Status.NOT_FOUND).build();
		}
		Pet updatedPet = petList.updatePet(updatePetObj, petId);
		return Response.ok(updatedPet).build();
	}

	//delete exiting pet from the petData
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Deleted pet", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))),
			@APIResponse(responseCode = "404", description = "No Pet found for this id.") })
	@DELETE
	@Path("/delete/{petId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deletePet(@PathParam("petId") Integer petId) {
		if(!petList.petExists(petId)){
			return Response.status(Status.NOT_FOUND).build();
		}
		boolean res = petList.deletePet(petId);
		return Response.ok(res).build();
	}
}
