package service;

import DTO.CommentDTO;
import DTO.CreateCommentDTO;
import DTO.CreatePostDTO;
import DTO.PostDTO;
import Entity.Comment;
import Entity.Like;
import Entity.Post;
import Entity.User;

import javax.ejb.Stateless;
import javax.persistence.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class PostService {

    @PersistenceContext(unitName = "hello")
    private EntityManager em;

    public PostDTO createPost(CreatePostDTO dto, int userId) {
        User user = em.find(User.class, userId);
        if (user != null) {
            Post post = new Post();
            post.setContent(dto.getContent());
            post.setImageUrl(dto.getImageUrl());
            post.setLink(dto.getLink());
            post.setAuthor(user);
            em.persist(post);
            return new PostDTO(post);
        }
        return null;
    }

    public List<PostDTO> getFeed(int userId) {
        // Modified query using a JOIN to efficiently retrieve the posts for the user and their friends
        String queryString = 
            "SELECT p FROM Post p " +
            "WHERE p.author.id = :userId " +
            "OR p.author IN (SELECT f FROM User u JOIN u.friends f WHERE u.id = :userId)";

        // Create the query and set the parameter for userId
        List<Post> posts = em.createQuery(queryString, Post.class)
                             .setParameter("userId", userId)
                             .getResultList();

        // Convert to PostDTO and return the result
        return posts.stream().map(PostDTO::new).collect(Collectors.toList());
    }




    public PostDTO updatePost(int postId, String newContent) {
        Post post = em.find(Post.class, postId);
        if (post != null) {
            post.setContent(newContent);
            em.merge(post);
            return new PostDTO(post);  // Return the updated DTO
        }
        return null;  // Not found
    }


    public void deletePost(int postId) {
        Post post = em.find(Post.class, postId);
        if (post != null) {
            em.remove(post);
        }
    }

    public PostDTO likePost(int postId, int userId) {
        Post post = em.find(Post.class, postId);
        User user = em.find(User.class, userId);

        if (post != null && user != null) {
            TypedQuery<Like> query = em.createQuery(
                "SELECT l FROM Like l WHERE l.post.id = :postId AND l.user.id = :userId", Like.class);
            query.setParameter("postId", postId);
            query.setParameter("userId", userId);

            if (query.getResultList().isEmpty()) {
                Like like = new Like();
                like.setPost(post);
                like.setUser(user);
                em.persist(like);
            }

            em.flush(); 
            return new PostDTO(post);  
        }

        return null;
    }


    public CommentDTO commentPost(int postId, int userId, CreateCommentDTO dto) {
        Post post = em.find(Post.class, postId);
        User user = em.find(User.class, userId);
        
        if (post != null && user != null) {
            Comment comment = new Comment();
            comment.setPost(post);
            comment.setUser(user);
            comment.setText(dto.getText());
            comment.setCreatedAt(new Date());
            em.persist(comment);

            // Flush to get ID and return the full DTO
            em.flush();

            return new CommentDTO(comment);  // This will include post + post.author + commenter
        }
        return null;
    }

}
