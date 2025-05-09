package DTO;

import Entity.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserProfileDTO {
    private Integer id;
    private String username;
    private String name;
    private List<PostDTO> posts;      // User's posts
    private List<UserDTO> friends;    // User's friends

    public UserProfileDTO(User user) {
        this.id = user.getId();
        this.username = user.getName();
        this.name = user.getName();

        // Map the user's posts to PostDTO
        this.posts = user.getPosts().stream()
                .map(PostDTO::new)  // Assuming PostDTO maps the Post entity properly
                .collect(Collectors.toList());

        // Map the user's friends to UserDTO
        this.friends = user.getFriends().stream()
                .map(UserDTO::new)  // Assuming UserDTO maps the User entity properly
                .collect(Collectors.toList());
    }

    // Getters
    public Integer getId() { return id; }
    public String getUsername() { return username; }
    public String getName() { return name; }
    public List<PostDTO> getPosts() { return posts; }
    public List<UserDTO> getFriends() { return friends; }
}
