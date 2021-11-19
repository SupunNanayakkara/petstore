package com.example.petstore;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.example.petstore.datasourse.PetTypeData;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

@Path("/v1/petTypes")
@Produces("application/json")
public class PetTypeResource {
    private PetTypeData petTypeList = PetTypeData.getInstance();

    //retrieve all the pet types from petTypeData
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "All Pet Types", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))) })
    @GET
    public Response getPetTypes() {
        List<PetType> petTypes = petTypeList.getPetTypeList();
        return Response.ok(petTypes).build();
    }

    //search pet type by pet type name
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Search pet type by name", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))),
            @APIResponse(responseCode = "404", description = "No Pet Type found under this type.") })
    @GET
    @Path("/searchByType/{petTypeName}")
    public Response getPetType(@PathParam("petTypeName") String petTypeName) {
        PetType petType = petTypeList.getPetTypeByName(petTypeName);
        if(petType == null){
            return Response.status(Status.NOT_FOUND).build();
        }
        return Response.ok(petType).build();
    }

    //add new pet type to petTypeData
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Add new pet type", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))),
            @APIResponse(responseCode = "404", description = "Error while adding") })
    @POST
    @Path("/new")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPetType(PetType petTypeObj) {
        PetType newPetType = petTypeList.addPetType(petTypeObj);
        return Response.ok(newPetType).build();
    }

    //update existing pet type
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Updated pet type", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))),
            @APIResponse(responseCode = "404", description = "No Pet Type found for this id.") })
    @PUT
    @Path("/edit/{petTypeId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePetType(PetType toBeUpdated, @PathParam("petTypeId") Integer petTypeId) {
        if(!petTypeList.petTypeExists(petTypeId)){
            return Response.status(Status.NOT_FOUND).build();
        }
        PetType updatedPetType = petTypeList.updatePetType(toBeUpdated, petTypeId);
        return Response.ok(updatedPetType).build();
    }

    //delete existing pet type from petTypeData
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Deleted pet type", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))),
            @APIResponse(responseCode = "404", description = "No Pet Type found for this id.") })
    @DELETE
    @Path("/delete/{petTypeId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deletePetType(@PathParam("petTypeId") Integer petTypeId) {
        if(!petTypeList.petTypeExists(petTypeId)){
            return Response.status(Status.NOT_FOUND).build();
        }
        boolean res = petTypeList.deletePetType(petTypeId);
        return Response.ok(res).build();
    }
}
