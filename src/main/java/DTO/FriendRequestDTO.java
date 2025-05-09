package DTO;

import Entity.FriendRequest;

public class FriendRequestDTO {
    private Integer id;
    private UserDTO sender;
    private UserDTO receiver;
    private String status;

    public FriendRequestDTO(FriendRequest request) {
        this.id = request.getId();
        this.sender = new UserDTO(request.getSender());
        this.receiver = new UserDTO(request.getReceiver());
        this.status = request.getStatus().name(); // or toString()
    }

    // Getters
    public Integer getId() { return id; }
    public UserDTO getSender() { return sender; }
    public UserDTO getReceiver() { return receiver; }
    public String getStatus() { return status; }
}
