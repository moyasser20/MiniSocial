package Entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String email;

    private String password;
    private String name;
    private String bio;
    private String role;

    // Sent Friend Requests
    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<FriendRequest> sentRequests;

    // Received Friend Requests
    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<FriendRequest> receivedRequests;

    // Posts
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Post> posts;

    // Comments
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Comment> comments;

    // Friends (ManyToMany relationship)
    @ManyToMany
    @JoinTable(
        name = "user_friends",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    private Set<User> friends;

    // Groups (ManyToMany relationship for members)
    @ManyToMany(mappedBy = "members")
    private Set<Groups> groups;

    // Admin of Groups (ManyToMany relationship for admins)
    @ManyToMany(mappedBy = "admins")
    private Set<Groups> adminGroups;

    public User() {}

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public Set<FriendRequest> getSentRequests() { return sentRequests; }
    public void setSentRequests(Set<FriendRequest> sentRequests) { this.sentRequests = sentRequests; }

    public Set<FriendRequest> getReceivedRequests() { return receivedRequests; }
    public void setReceivedRequests(Set<FriendRequest> receivedRequests) { this.receivedRequests = receivedRequests; }

    public Set<Post> getPosts() { return posts; }
    public void setPosts(Set<Post> posts) { this.posts = posts; }

    public Set<Comment> getComments() { return comments; }
    public void setComments(Set<Comment> comments) { this.comments = comments; }

    public Set<User> getFriends() { return friends; }
    public void setFriends(Set<User> friends) { this.friends = friends; }

    public Set<Groups> getGroups() { return groups; }
    public void setGroups(Set<Groups> groups) { this.groups = groups; }

    public Set<Groups> getAdminGroups() { return adminGroups; }
    public void setAdminGroups(Set<Groups> adminGroups) { this.adminGroups = adminGroups; }
}
