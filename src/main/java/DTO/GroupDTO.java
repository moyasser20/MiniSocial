package DTO;

import Entity.Groups;
import Entity.User;

import java.util.List;
import java.util.stream.Collectors;

public class GroupDTO {

    private Integer groupId;
    private String name;
    private String description;
    private String creatorName;
    private Boolean isOpen;

    private List<Integer> memberIds;
    private List<Integer> adminIds;

    // ✅ NEW: Add group post DTOs
    private List<GroupPostDTO> posts;

    public GroupDTO(Groups group) {
        this.groupId = group.getGroupId();
        this.name = group.getName();
        this.description = group.getDescription();
        this.creatorName = group.getCreator() != null ? group.getCreator().getName() : null;
        this.isOpen = group.getIsOpen();

        this.memberIds = group.getMembers() != null
                ? group.getMembers().stream().map(User::getId).collect(Collectors.toList())
                : null;

        this.adminIds = group.getAdmins() != null
                ? group.getAdmins().stream().map(User::getId).collect(Collectors.toList())
                : null;

        // ✅ Convert GroupPost entities to DTOs
        this.posts = group.getPosts() != null
                ? group.getPosts().stream().map(GroupPostDTO::new).collect(Collectors.toList())
                : null;
    }

    public Integer getGroupId() { return groupId; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getCreatorName() { return creatorName; }
    public Boolean getIsOpen() { return isOpen; }
    public List<Integer> getMemberIds() { return memberIds; }
    public List<Integer> getAdminIds() { return adminIds; }
    public List<GroupPostDTO> getPosts() { return posts; } // ✅ Getter for posts
}
