package DTO;

import Entity.Post;

public class SimplePostDTO {
    private Integer id;
    private String content;
    private UserDTO author;

    public SimplePostDTO(Post post) {
        this.id = post.getId();
        this.content = post.getContent();
        if (post.getAuthor() != null) {
            this.author = new UserDTO(post.getAuthor());
        }
    }

    // Getters
    public Integer getId() { return id; }
    public String getContent() { return content; }
    public UserDTO getAuthor() { return author; }
}
