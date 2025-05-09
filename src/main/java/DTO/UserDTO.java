package DTO;

import Entity.User;
import java.util.List;
import java.util.stream.Collectors;

public class UserDTO {
    private Integer id;
    private String name;
    private String email;
    private String bio;
    private List<GroupDTO> groups;          // List of groups the user is a member of
    private List<GroupDTO> adminGroups;      // List of groups the user is an admin of
    // private List<FriendDTO> friends; // Keeping this commented as per your request

    public UserDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.bio = user.getBio();

        // Mapping groups the user is a member of
        this.groups = user.getGroups().stream()
                           .map(GroupDTO::new)
                           .collect(Collectors.toList());

        // Mapping groups the user is an admin of
        this.adminGroups = user.getAdminGroups().stream()
                               .map(GroupDTO::new)
                               .collect(Collectors.toList());

        // Uncomment below if you decide to add friends in the future
        // this.friends = user.getFriends().stream()
        //                    .map(FriendDTO::new)
        //                    .collect(Collectors.toList());
    }

    public Integer getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getBio() { return bio; }
    public List<GroupDTO> getGroups() { return groups; }
    public List<GroupDTO> getAdminGroups() { return adminGroups; }
    // public List<FriendDTO> getFriends() { return friends; } // Keeping this commented
}
