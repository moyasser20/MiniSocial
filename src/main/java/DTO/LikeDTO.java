package DTO;

import Entity.Like;

public class LikeDTO {
    private Integer id;
    private UserDTO user;

    public LikeDTO(Like like) {
        this.id = like.getId();

        // Check if like.getUser() is null before creating UserDTO
        if (like.getUser() != null) {
            this.user = new UserDTO(like.getUser());
        } else {
            this.user = null;  // Or handle appropriately
        }
    }

    // Getters
    public Integer getId() { return id; }
    public UserDTO getUser() { return user; }
}
