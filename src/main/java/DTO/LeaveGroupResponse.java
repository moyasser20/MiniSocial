package DTO;

public class LeaveGroupResponse {
    private String message;
    private UserDTO user;

    public LeaveGroupResponse(String message, UserDTO user) {
        this.message = message;
        this.user = user;
    }

    public String getMessage() { return message; }
    public UserDTO getUser() { return user; }
}
