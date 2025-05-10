package DTO;

public class LeaveGroupDTO {
    private Integer userId;
    private Integer groupId;

    public LeaveGroupDTO() {}

    public LeaveGroupDTO(Integer userId, Integer groupId) {
        this.userId = userId;
        this.groupId = groupId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }
}
