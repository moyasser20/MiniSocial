package service;

import DTO.FriendRequestDTO;
import DTO.UserDTO;
import Entity.FriendRequest;
import Entity.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class FriendRequestService {

    @PersistenceContext(unitName = "hello")
    private EntityManager em;

    // Send a friend request
    public void sendRequest(Integer senderId, Integer receiverId) {
        User sender = em.find(User.class, senderId);
        User receiver = em.find(User.class, receiverId);

        if (sender == null || receiver == null) {
            throw new IllegalArgumentException("Sender or receiver not found");
        }

        FriendRequest request = new FriendRequest();
        request.setSender(sender);
        request.setReceiver(receiver);
        request.setStatus(FriendRequest.Status.PENDING);

        em.persist(request); // Save the request in DB
    }

    // Respond to a friend request (ACCEPT or REJECT)
    public void respondToRequest(Integer requestId, String response) {
        FriendRequest request = em.find(FriendRequest.class, requestId);

        if (request != null && request.getStatus() == FriendRequest.Status.PENDING) {
            if ("ACCEPTED".equalsIgnoreCase(response)) {
                request.setStatus(FriendRequest.Status.ACCEPTED);
                em.merge(request);
            } else if ("REJECTED".equalsIgnoreCase(response)) {
                request.setStatus(FriendRequest.Status.REJECTED);
            } else {
                throw new IllegalArgumentException("Invalid response. Must be 'ACCEPTED' or 'REJECTED'");
            }
            em.merge(request); // Merge updated request into DB
        } else {
            throw new IllegalArgumentException("Request not found or already responded to.");
        }
    }

    // Get all pending friend requests for a specific user
    public List<FriendRequestDTO> getPendingRequestsForUser(Integer userId) {
        List<FriendRequest> requests = em.createQuery(
                "SELECT fr FROM FriendRequest fr " +
                "WHERE (fr.sender.id = :userId OR fr.receiver.id = :userId) " +
                "AND fr.status = :status", 
                FriendRequest.class
        )
        .setParameter("userId", userId)
        .setParameter("status", FriendRequest.Status.PENDING)
        .getResultList();

        List<FriendRequestDTO> pendingRequestDTOs = new ArrayList<>();
        for (FriendRequest request : requests) {
            pendingRequestDTOs.add(new FriendRequestDTO(request));
        }

        return pendingRequestDTOs;
    }


    public List<UserDTO> getFriendsList(Integer userId) {
        List<User> sent = em.createQuery(
            "SELECT fr.receiver FROM FriendRequest fr " +
            "WHERE fr.sender.id = :userId AND fr.status = :status", User.class)
            .setParameter("userId", userId)
            .setParameter("status", FriendRequest.Status.ACCEPTED)
            .getResultList();

        List<User> received = em.createQuery(
            "SELECT fr.sender FROM FriendRequest fr " +
            "WHERE fr.receiver.id = :userId AND fr.status = :status", User.class)
            .setParameter("userId", userId)
            .setParameter("status", FriendRequest.Status.ACCEPTED)
            .getResultList();

        List<UserDTO> result = new ArrayList<>();
        for (User user : sent) result.add(new UserDTO(user));
        for (User user : received) result.add(new UserDTO(user));

        return result;
    }

}
