package Entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class GroupPosts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer groupPostId;

    @ManyToOne
    @JoinColumn(name = "posting_user_id")
    private User postingUser;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Groups group;

    @Column(length = 1000)
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    // === Getters and Setters ===

    public Integer getGroupPostId() {
        return groupPostId;
    }

    public void setGroupPostId(Integer groupPostId) {
        this.groupPostId = groupPostId;
    }

    public User getPostingUser() {
        return postingUser;
    }

    public void setPostingUser(User postingUser) {
        this.postingUser = postingUser;
    }

    public Groups getGroup() {
        return group;
    }

    public void setGroup(Groups group) {
        this.group = group;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
