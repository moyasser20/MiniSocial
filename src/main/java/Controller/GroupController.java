package Controller;

import DTO.CreateGroupPostDTO;
import DTO.GroupDTO;
import DTO.GroupPostDTO;
import DTO.GroupRequestDTO;
import Entity.Groups;
import Entity.User;
import service.GroupService;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/group")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class GroupController {

    @EJB
    private GroupService grpService;

    @POST
    @Path("/create")
    public Response createGroup(Groups group, @QueryParam("userId") int userId) {
        User user = new User(); user.setId(userId);
        GroupDTO dto = grpService.createGroup(group, user);
        return Response.ok(dto).build();
    }

    @POST
    @Path("/join")
    public Response requestJoin(@QueryParam("groupId") int groupId, @QueryParam("userId") int userId) {
        User user = new User(); user.setId(userId);
        Groups group = new Groups(); group.setGroupId(groupId);
        GroupRequestDTO dto = grpService.requestToJoinGroup(user, group);
        return Response.ok(dto).build();
    }

    @POST
    @Path("/leave")
    public Response leaveGroup(@QueryParam("groupId") int groupId, @QueryParam("userId") int userId) {
        User user = new User(); user.setId(userId);
        Groups group = new Groups(); group.setGroupId(groupId);
        grpService.leaveGroup(user, group);
        return Response.ok("Left group").build();
       
    }	
    @POST
    @Path("/post")
    public Response createGroupPost(@QueryParam("userId") int userId,
            @QueryParam("groupId") int groupId,
            CreateGroupPostDTO dto) {
        try {
            // محاولة إنشاء البوست
            GroupPostDTO created = grpService.createGroupPost(dto, userId, groupId);
            return Response.ok(created).build(); // إرجاع النتيجة
        } catch (IllegalArgumentException e) {
            // إذا كان في مشكلة في وجود المستخدم أو المجموعة
            System.out.println("Error: " + e.getMessage()); // طباعة الخطأ في الكونسول
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"" + e.getMessage() + "\"}").build(); // إرجاع رد خطأ
        } catch (Exception e) {
            // التعامل مع الأخطاء الأخرى غير المتوقعة
            e.printStackTrace(); // طباعة الاستثناء
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\":\"An internal server error occurred\"}").build(); // إرجاع رد خطأ داخلي
        }
    }



    @GET
    @Path("/{groupId}/posts")
    public Response getGroupPosts(@PathParam("groupId") int groupId) {
        List<GroupPostDTO> posts = grpService.getPostsForGroup(groupId);
        return Response.ok(posts).build();
    }

    @POST
    @Path("/approve")
    public Response approveRequest(@QueryParam("requestId") int requestId, @QueryParam("adminId") int adminId) {
        User admin = new User(); admin.setId(adminId);
        GroupRequestDTO dto = grpService.approveJoinRequest(requestId, admin);
        return Response.ok(dto).build();
    }

    @POST
    @Path("/reject")
    public Response rejectRequest(@QueryParam("requestId") int requestId, @QueryParam("adminId") int adminId) {
        User admin = new User(); admin.setId(adminId);
        GroupRequestDTO dto = grpService.rejectJoinRequest(requestId, admin);
        return Response.ok(dto).build();
    }

    @DELETE
    @Path("/delete")
    public Response deleteGroup(@QueryParam("groupId") int groupId, @QueryParam("adminId") int adminId) {
        Groups group = new Groups(); group.setGroupId(groupId);
        User admin = new User(); admin.setId(adminId);
        grpService.deleteGroup(group, admin);
        return Response.ok("Group deleted").build();
    }

    @POST
    @Path("/promote")
    public Response promoteAdmin(@QueryParam("groupId") int groupId, @QueryParam("userId") int userId) {
        Groups group = new Groups(); group.setGroupId(groupId);
        User user = new User(); user.setId(userId);
        GroupDTO dto = grpService.promoteToGrpAdmin(group, user);
        return Response.ok(dto).build();
    }

    @POST
    @Path("/demote")
    public Response demoteAdmin(@QueryParam("groupId") int groupId, @QueryParam("userId") int userId) {
        Groups group = new Groups(); group.setGroupId(groupId);
        User user = new User(); user.setId(userId);
        GroupDTO dto = grpService.demoteFromGrpAdmin(group, user);
        return Response.ok(dto).build();
    }

    @GET
    @Path("/all")
    public Response getAllGroups() {
        List<GroupDTO> groups = grpService.getAllGroups();
        return Response.ok(groups).build();
    }

    @GET
    @Path("/requests")
    public Response getGroupRequests(@QueryParam("groupId") int groupId) {
        List<GroupRequestDTO> requests = grpService.getRequestsForGroup(groupId);
        return Response.ok(requests).build();
    }
}
