package DTO;

import Entity.User;

public class FriendDTO {
    private Integer id;
    private String name;

    public FriendDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
    }

    public Integer getId() { return id; }
    public String getName() { return name; }
}
