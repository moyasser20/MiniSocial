package DTO;

import Entity.GroupRequest;

public class GroupRequestDTO {

    private Integer requestId;
    private Integer requestingUserId;
    private String requestingUserName;
    private Integer groupId;
    private String groupName;
    private String status;

    public GroupRequestDTO(GroupRequest request) {
        this.requestId = request.getRequestId();

        if (request.getRequestingUser() != null) {
            this.requestingUserId = request.getRequestingUser().getId();
            this.requestingUserName = request.getRequestingUser().getName();
        }

        if (request.getGroup() != null) {
            this.groupId = request.getGroup().getGroupId();
            this.groupName = request.getGroup().getName();
        }

        this.status = request.getStatus() != null ? request.getStatus().name() : null;
    }

    public Integer getRequestId() { return requestId; }
    public Integer getRequestingUserId() { return requestingUserId; }
    public String getRequestingUserName() { return requestingUserName; }
    public Integer getGroupId() { return groupId; }
    public String getGroupName() { return groupName; }
    public String getStatus() { return status; }
}
