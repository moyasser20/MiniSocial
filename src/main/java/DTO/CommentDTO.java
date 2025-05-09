package DTO;

import Entity.Comment;

import java.util.Date;

public class CommentDTO {
    private Integer id;
    private String text;
    private Date createdAt;
    private UserDTO author;            // The user who made the comment
    private SimplePostDTO post;        // The post being commented on

    public CommentDTO(Comment comment) {
        this.id = comment.getId();
        this.text = comment.getText();
        this.createdAt = comment.getCreatedAt();

        // Check if comment.getUser() is null before creating UserDTO
        if (comment.getUser() != null) {
            this.author = new UserDTO(comment.getUser());
        } else {
            this.author = null;  // Handle appropriately if the user is null
        }

        // Set the post and post author
        if (comment.getPost() != null) {
            this.post = new SimplePostDTO(comment.getPost());
        } else {
            this.post = null;  // Handle appropriately if the post is null
        }
    }

    // Getters
    public Integer getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public UserDTO getAuthor() {
        return author;
    }

    public SimplePostDTO getPost() {
        return post;
    }
}
