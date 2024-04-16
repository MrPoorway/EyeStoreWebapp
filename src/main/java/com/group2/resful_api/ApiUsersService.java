package com.group2.resful_api;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Base64;

import com.group2.model.StoreException;
import com.group2.model.User;
import com.group2.service.impl.UserServiceImpl;


@Path("/users")
public class ApiUsersService {
    
    private UserServiceImpl UserServiceImpl = new UserServiceImpl();
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getallUsers_JSON() throws StoreException {
        return UserServiceImpl.getAllUser();
    }
//    
//    @POST
//    @Path("/checklogin")
//    @Produces(MediaType.APPLICATION_JSON)
//    public String checkLogin(@HeaderParam("authorization") String auth){
//        if(isAuthorization(auth)) {
//            return "true";
//            } else {
//                return "false";
//            }
//    }
//    
//    private boolean isAuthorization(String auth) {
//    	String decodeString = "";
//        try {
//            decodeString = new String(Base64.getDecoder().decode(auth), "UTF-8");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        String[] split = decodeString.split(":");
//        String username = split[0];
//        String password = split[1];
//        return UserServiceImpl.authorization(username, password);
//    }


    
}