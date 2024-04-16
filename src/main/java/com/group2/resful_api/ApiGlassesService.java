package com.group2.resful_api;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.group2.model.Glasses;
import com.group2.model.StoreException;
import com.group2.service.impl.GlassesServiceImpl;


@Path("/glassess")
public class ApiGlassesService {
    
	private GlassesServiceImpl GlassesServiceImpl = new GlassesServiceImpl();
	
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Glasses> getallGlasses_JSON() throws StoreException {
    	return GlassesServiceImpl.getAllGlasses();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addGlasses(Glasses book) throws StoreException {
    	return Response.ok(GlassesServiceImpl.addGlasses(book)).build();
    }
    
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteGlassesById(@PathParam("id") String id) throws StoreException {
        return Response.ok(GlassesServiceImpl.deleteGlassesById(id)).build();
    }
    
    @PUT 
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateGlasses(Glasses glasses) throws StoreException {
        return Response.ok(GlassesServiceImpl.updateGlasses(glasses)).build();
    }
    
    //get imageUri
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    @Path("/{id}/imageUri")
//    public Response getImageUri(@PathParam("id") String id) throws StoreException {
//        return Response.ok(GlassesServiceImpl.getImageUri(id)).build();
//    }
    
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    @Path("/{id}/imageUri")
//    public Response getImageUri(@PathParam("id") String id) throws StoreException {
//        return Response.ok(GlassesServiceImpl.getImageUri(id)).build();
//    }
    
    
//    @GET
//    @Path("/{id}/imageUri")
//    public String getImageUri(@PathParam("id") String id) throws StoreException {
//        String imageUri = GlassesServiceImpl.getImageUri(id);
//        return imageUri;
//    }
//    
//    @GET
//    @Produces(MediaType.TEXT_PLAIN)
//    @Path("/{id}/imageUri")
//    public Response getImageUri(@PathParam("id") String id) throws StoreException {
//        String imageUri = GlassesServiceImpl.getImageUri(id);
//        return Response.ok(imageUri).build();
//    }

    
}
