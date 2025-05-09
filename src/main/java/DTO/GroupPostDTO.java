package DTO;

import Entity.GroupPosts;

import java.util.Date;

public class GroupPostDTO {

    private Integer groupPostId;
    private Integer postingUserId;
    private String postingUserName;
    private Integer groupId;
    private String groupName;
    private String content;
    private Date timestamp;

    public GroupPostDTO(GroupPosts post) {
        this.groupPostId = post.getGroupPostId();

        if (post.getPostingUser() != null) {
            this.postingUserId = post.getPostingUser().getId();
            this.postingUserName = post.getPostingUser().getName();
        }

        if (post.getGroup() != null) {
            this.groupId = post.getGroup().getGroupId();
            this.groupName = post.getGroup().getName();
        }

        this.content = post.getContent();
        this.timestamp = post.getTimestamp();
    }

    public Integer getGroupPostId() { return groupPostId; }
    public Integer getPostingUserId() { return postingUserId; }
    public String getPostingUserName() { return postingUserName; }
    public Integer getGroupId() { return groupId; }
    public String getGroupName() { return groupName; }
    public String getContent() { return content; }
    public Date getTimestamp() { return timestamp; }
}
