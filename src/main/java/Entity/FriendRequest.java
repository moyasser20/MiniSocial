	package Entity;
	
	import java.util.Date;
	import javax.persistence.*;
	
	@Entity
	public class FriendRequest {
	
	    public enum Status {
	        PENDING,
	        ACCEPTED,
	        REJECTED
	    }
	
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer id;
	
	    @ManyToOne
	    private User sender;
	
	    @ManyToOne
	    private User receiver;
	
	    @Enumerated(EnumType.STRING)
	    private Status status;
	
//	    @Temporal(TemporalType.TIMESTAMP)
//	    private Date sentAt;
//	
	    public FriendRequest() {
	        this.status = Status.PENDING;
	        //this.sentAt = new Date();
	    }
	
	    // Getters & Setters
	
	    public Integer getId() {
	        return id;
	    }
	
	    public void setId(Integer id) {
	        this.id = id;
	    }
	
	    public User getSender() {
	        return sender;
	    }
	
	    public void setSender(User sender) {
	        this.sender = sender;
	    }
	
	    public User getReceiver() {
	        return receiver;
	    }
	
	    public void setReceiver(User receiver) {
	        this.receiver = receiver;
	    }
	
	    public Status getStatus() {
	        return status;
	    }
	
	    public void setStatus(Status status) {
	        this.status = status;
	    }
	
//	    public Date getSentAt() {
//	        return sentAt;
//	    }
//	
//	    public void setSentAt(Date sentAt) {
//	        this.sentAt = sentAt;
//	    }
//	    
	    
	}
