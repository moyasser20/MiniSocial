package Controller;

import DTO.FriendRequestDTO;
import DTO.UserDTO;
import service.FriendRequestService;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/friends")
@Produces(MediaType.APPLICATION_JSON) 
@Consumes(MediaType.APPLICATION_JSON)
public class FriendRequestController {

    @EJB
    private FriendRequestService friendRequestService;

    // Send a Friend Request
    @POST
    @Path("/send")
    public Response sendRequest(@QueryParam("senderId") Integer senderId,
                                @QueryParam("receiverId") Integer receiverId) {
        try {
            friendRequestService.sendRequest(senderId, receiverId);
            return Response.ok("{\"message\":\"Friend request sent successfully\"}").build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("{\"error\":\"" + e.getMessage() + "\"}")
                           .build();
        }
    }

    // Respond to a Friend Request (ACCEPTED or REJECTED)
    @POST
    @Path("/respond")
    public Response respondToRequest(@QueryParam("requestId") Integer requestId,
                                     @QueryParam("response") String response) {
        try {
            friendRequestService.respondToRequest(requestId, response);
            return Response.ok("{\"message\":\"Friend request " + response.toLowerCase() + "\"}").build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("{\"error\":\"" + e.getMessage() + "\"}")
                           .build();
        }
    }

    // Get all pending requests for a specific user
    @GET
    @Path("/pending/{userId}")
    public Response getPendingRequests(@PathParam("userId") Integer userId) {
        List<FriendRequestDTO> pendingRequests = friendRequestService.getPendingRequestsForUser(userId);
        return Response.ok(pendingRequests).build();
    }

    // Get the list of friends for a user
    @GET
    @Path("/list/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFriends(@PathParam("userId") Integer userId) {
        List<UserDTO> friends = friendRequestService.getFriendsList(userId);
        return Response.ok(friends).build();
    }
}
