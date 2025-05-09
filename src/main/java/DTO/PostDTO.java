package DTO;

import Entity.Post;
import Entity.Comment;
import Entity.Like;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class PostDTO {
    private Integer id;
    private String content;
    private String imageUrl;
    private String link;
    private Date createdAt;
    private UserDTO author;
    private List<CommentDTO> comments;
    private List<LikeDTO> likes;

    public PostDTO(Post post) {
        this.id = post.getId();
        this.content = post.getContent();
        this.imageUrl = post.getImageUrl();
        this.link = post.getLink();
        this.createdAt = post.getCreatedAt();
        this.author = post.getAuthor() != null ? new UserDTO(post.getAuthor()) : null;

        // Include actual comments
        if (post.getComments() != null) {
            this.comments = post.getComments().stream()
                    .map(CommentDTO::new)
                    .collect(Collectors.toList());
        } else {
            this.comments = new ArrayList<>();
        }

        // Include likes
        if (post.getLikes() != null) {
            this.likes = post.getLikes().stream()
                    .map(LikeDTO::new)
                    .collect(Collectors.toList());
        } else {
            this.likes = new ArrayList<>();
        }
    }

    // Getters
    public Integer getId() { return id; }
    public String getContent() { return content; }
    public String getImageUrl() { return imageUrl; }
    public String getLink() { return link; }
    public Date getCreatedAt() { return createdAt; }
    public UserDTO getAuthor() { return author; }
    public List<CommentDTO> getComments() { return comments; }
    public List<LikeDTO> getLikes() { return likes; }
}
