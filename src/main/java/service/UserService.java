package service;

import Entity.User;
import DTO.UserDTO;
import DTO.UserProfileDTO;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class UserService {

    @PersistenceContext(unitName = "hello")
    private EntityManager em;

    // Register a new user
    public void register(User user) {
        em.persist(user);  // Persist user to the database
    }

 // Login a user by verifying email and password
    public UserDTO login(String email, String password) {
        try {
            // Fetch user by email
            User user = em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                          .setParameter("email", email)
                          .getSingleResult();

            // Check if the password matches
            if (user.getPassword().equals(password)) {
                return new UserDTO(user);  // Return the DTO instead of the full User entity
            } else {
                return null;
            }

        } catch (NoResultException e) {
            return null;  // Return null if no user found or password doesn't match
        }
    }

    // Update the profile of an existing user
    public void updateProfile(Integer id, User updatedUser) {
        User user = em.find(User.class, id);  // Find user by ID
        if (user != null) {
            // Update user fields if present in updatedUser
            user.setName(updatedUser.getName());
            user.setBio(updatedUser.getBio());
            user.setEmail(updatedUser.getEmail());
            user.setRole(updatedUser.getRole());

            // Update password if new one is provided
            if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                user.setPassword(updatedUser.getPassword());
            }

            em.merge(user);  // Save the updated user entity to the database
        }
    }

    // Find a user by their ID
    public User findbyId(Integer id) {
        return em.find(User.class, id);  // Fetch user by ID
    }

    // Get all users and return as a list of UserDTO
    public List<UserDTO> getAllUserDTOs() {
        List<User> users = getAllUsers();  // Get all users from the database
        return users.stream()  // Convert each User to UserDTO
                    .map(UserDTO::new)
                    .collect(Collectors.toList());  // Collect the result into a list
    }

    private List<User> getAllUsers() {
        // Query to fetch all users from the database
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u", User.class);
        return query.getResultList();  // Execute the query and return the result as a List<User>
    }

    public UserProfileDTO getUserProfile(int userId) {
        // Fetch the user from the database
        User user = em.find(User.class, userId);

        if (user != null) {
            // Return the aggregated user profile data
            return new UserProfileDTO(user);
        }

        return null;  // User not found
    }


}
