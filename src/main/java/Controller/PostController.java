package Controller;

import DTO.CommentDTO;
import DTO.CreateCommentDTO;
import DTO.CreatePostDTO;
import DTO.PostDTO;
import service.PostService;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/posts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PostController {

    @EJB
    private PostService postService;

    @POST
    @Path("/create/{userId}")
    public Response createPost(@PathParam("userId") int userId, CreatePostDTO dto) {
        PostDTO createdPost = postService.createPost(dto, userId);
        if (createdPost != null) {
            return Response.ok(createdPost).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"User not found\"}").build();
        }
    }

    @GET
    @Path("/feed/{userId}")
    public Response getFeed(@PathParam("userId") int userId) {
        List<PostDTO> feed = postService.getFeed(userId);
        return Response.ok(feed).build();
    }

    @PUT
    @Path("/update/{postId}")
    public Response updatePost(@PathParam("postId") int postId, String requestBody) {
        try (javax.json.JsonReader reader = javax.json.Json.createReader(new java.io.StringReader(requestBody))) {
            javax.json.JsonObject json = reader.readObject();
            String newContent = json.getString("content");

            PostDTO updatedPost = postService.updatePost(postId, newContent);
            if (updatedPost != null) {
                return Response.ok(updatedPost).build();  // Return the full DTO
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"error\":\"Post not found\"}").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Invalid JSON input\"}").build();
        }
    }



    @DELETE
    @Path("/delete/{postId}")
    public Response deletePost(@PathParam("postId") int postId) {
        postService.deletePost(postId);
        return Response.ok("{\"message\":\"Post deleted successfully\"}").build();
    }

    @POST
    @Path("/{postId}/like/{userId}")
    public Response likePost(@PathParam("postId") int postId, @PathParam("userId") int userId) {
        PostDTO postAfterLike = postService.likePost(postId, userId);
        if (postAfterLike != null) {
            return Response.ok(postAfterLike).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"Post or User not found\"}").build();
        }
    }



    @POST
    @Path("/{postId}/comment/{userId}")
    public Response commentPost(@PathParam("postId") int postId,
                                @PathParam("userId") int userId,
                                CreateCommentDTO dto) {
        CommentDTO commentDTO = postService.commentPost(postId, userId, dto);
        if (commentDTO != null) {
            return Response.ok(commentDTO).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"Post or User not found\"}").build();
        }
    }

}
