package service;

import DTO.CreateGroupPostDTO;
import DTO.GroupDTO;
import DTO.GroupPostDTO;
import DTO.GroupRequestDTO;
import DTO.LeaveGroupDTO;
import DTO.LeaveGroupResponse;
import DTO.UserDTO;
import Entity.GroupPosts;
import Entity.GroupRequest;
import Entity.GroupRequest.MembershipStatus;
import Entity.Groups;
import Entity.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class GroupService {

    @PersistenceContext(unitName = "hello")
    private EntityManager em;

    public GroupDTO createGroup(Groups grp, User user) {
        grp.setCreator(user);
        grp.getMembers().add(user);
        grp.getAdmins().add(user);
        em.persist(grp);
        return new GroupDTO(grp);
    }

    public GroupRequestDTO requestToJoinGroup(User user, Groups group) {
        GroupRequest request = new GroupRequest();
        request.setGroup(em.find(Groups.class, group.getGroupId()));
        request.setRequestingUser(em.find(User.class, user.getId()));
        request.setStatus(MembershipStatus.PENDING);
        em.persist(request);
        return new GroupRequestDTO(request);
    }

 // In your service class
    public LeaveGroupResponse leaveGroup(int userId, int groupId) {
        Groups group = em.find(Groups.class, groupId);
        User user = em.find(User.class, userId);

        if (group == null || user == null) {
            throw new IllegalArgumentException("User or Group not found.");
        }

        group.getMembers().remove(user);
        group.getAdmins().remove(user); // Safe even if not an admin

        em.merge(group);

        String message = "User " + user.getId() + " left group " + group.getGroupId();
        return new LeaveGroupResponse(message, new UserDTO(user));
    }


    public GroupPostDTO createGroupPost(CreateGroupPostDTO dto, int userId, int groupId) {
        System.out.println("Looking for user with ID: " + userId);
        User user = em.find(User.class, userId); // Attempt to fetch the user
        System.out.println("User found: " + (user != null));

        System.out.println("Looking for group with ID: " + groupId);
        Groups group = em.find(Groups.class, groupId); // Attempt to fetch the group
        System.out.println("Group found: " + (group != null));

        if (user == null && group == null) {
            String errorMessage = "Both User and Group not found";
            System.out.println(errorMessage);
            throw new IllegalArgumentException(errorMessage); // Throwing exception if both are not found
        }

        if (user == null) {
            String errorMessage = "User with ID " + userId + " not found";
            System.out.println(errorMessage);
            throw new IllegalArgumentException(errorMessage); // Throwing exception if user is not found
        }

        if (group == null) {
            String errorMessage = "Group with ID " + groupId + " not found";
            System.out.println(errorMessage);
            throw new IllegalArgumentException(errorMessage); // Throwing exception if group is not found
        }

        GroupPosts post = new GroupPosts();
        post.setPostingUser(user);
        post.setGroup(group);
        post.setContent(dto.getContent());
        post.setTimestamp(new java.util.Date()); // Set the current timestamp

        em.persist(post); // Save the post in the database
        return new GroupPostDTO(post); // Return the created post as a DTO
    }



    // Get all posts for a group
    public List<GroupPostDTO> getPostsForGroup(int groupId) {
        List<GroupPosts> posts = em.createQuery("SELECT p FROM GroupPosts p WHERE p.group.groupId = :gid ORDER BY p.timestamp DESC", GroupPosts.class)
                .setParameter("gid", groupId)
                .getResultList();

        return posts.stream().map(GroupPostDTO::new).collect(Collectors.toList());
    }

    public GroupRequestDTO approveJoinRequest(Integer requestId, User adminUser) {
        GroupRequest request = em.find(GroupRequest.class, requestId);
        if (request == null) {
            throw new IllegalArgumentException("Request not found");
        }

        // Removed admin check; anyone can approve requests
        request.setStatus(MembershipStatus.APPROVED);
        Groups group = request.getGroup();
        group.getMembers().add(request.getRequestingUser());

        em.merge(group);
        em.merge(request);
        return new GroupRequestDTO(request);
    }

    public GroupRequestDTO rejectJoinRequest(Integer requestId, User adminUser) {
        GroupRequest request = em.find(GroupRequest.class, requestId);
        if (request == null) {
            throw new IllegalArgumentException("Request not found");
        }

        // Removed admin check; anyone can reject requests
        request.setStatus(MembershipStatus.REJECTED);
        em.merge(request);
        return new GroupRequestDTO(request);
    }

    public void deleteGroup(Groups grp, User admin) {
        Groups managed = em.find(Groups.class, grp.getGroupId());
        if (managed == null) {
            throw new IllegalArgumentException("Group not found");
        }

        // Removed creator/admin check; anyone can delete the group (though this could be dangerous)
        em.remove(managed);
    }

    public GroupDTO promoteToGrpAdmin(Groups grp, User promotedUser) {
        Groups managedGroup = em.find(Groups.class, grp.getGroupId());
        User managedUser = em.find(User.class, promotedUser.getId());

        // Removed admin check; anyone can promote another user to admin
        managedGroup.getAdmins().add(managedUser);
        em.merge(managedGroup);

        return new GroupDTO(managedGroup);
    }

    public GroupDTO demoteFromGrpAdmin(Groups grp, User demotedUser) {
        Groups managedGroup = em.find(Groups.class, grp.getGroupId());
        User managedUser = em.find(User.class, demotedUser.getId());

        // Removed admin check; anyone can demote any admin (except the group creator)
        if (managedGroup.getCreator().getId().equals(managedUser.getId())) {
            throw new IllegalArgumentException("Cannot demote the group creator.");
        }

        managedGroup.getAdmins().remove(managedUser);
        em.merge(managedGroup);

        return new GroupDTO(managedGroup);
    }

    public List<GroupDTO> getAllGroups() {
        return em.createQuery("SELECT g FROM Groups g", Groups.class)
                .getResultList()
                .stream()
                .map(GroupDTO::new)
                .collect(Collectors.toList());
    }

    public List<GroupRequestDTO> getRequestsForGroup(int groupId) {
        return em.createQuery("SELECT r FROM GroupRequest r WHERE r.group.groupId = :gid", GroupRequest.class)
                .setParameter("gid", groupId)
                .getResultList()
                .stream()
                .map(GroupRequestDTO::new)
                .collect(Collectors.toList());
    }
}
