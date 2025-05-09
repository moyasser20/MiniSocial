package Controller;

import Entity.User;
import DTO.UserDTO;
import DTO.UserProfileDTO;
import service.UserService;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserController {

    @EJB
    private UserService userService;

    // Register a new user
    @POST
    @Path("/register")
    public Response register(User user) {
        try {
            userService.register(user);
            return Response.ok("{\"message\":\"User registered successfully\"}").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("{\"message\":\"" + e.getMessage() + "\"}").build();
        }
    }

    // Login a user by email and password
    @POST
    @Path("/login")
    public Response login(User loginUser) {
        User user = userService.login(loginUser.getEmail(), loginUser.getPassword());
        if (user != null) {
            return Response.ok(user).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED)
                           .entity("{\"message\":\"Invalid credentials\"}").build();
        }
    }

    // Update profile of a user by ID
    @PUT
    @Path("/{id}/update")
    public Response updateProfile(@PathParam("id") Integer id, User updatedUser) {
        try {
            userService.updateProfile(id, updatedUser);
            return Response.ok("{\"message\":\"Profile updated successfully\"}").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("{\"message\":\"" + e.getMessage() + "\"}").build();
        }
    }

    // Get a user by ID
    @GET
    @Path("/{id}")
    public Response getUser(@PathParam("id") Integer id) {
        User user = userService.findbyId(id);
        if (user != null) {
            return Response.ok(user).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("{\"message\":\"User not found\"}").build();
        }
    }

    // Get all users as a list of UserDTOs
    @GET
    @Path("/all")
    public Response getAllUsers() {
        try {
            // Get all users as DTOs (UserDTO)
            return Response.ok(userService.getAllUserDTOs()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("{\"message\":\"" + e.getMessage() + "\"}").build();
        }
    }
    
    @GET
    @Path("/{userId}/profile")
    public Response getUserProfile(@PathParam("userId") int userId) {
        UserProfileDTO userProfile = userService.getUserProfile(userId);

        if (userProfile != null) {
            return Response.ok(userProfile).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"User not found\"}").build();
        }
    }
}
