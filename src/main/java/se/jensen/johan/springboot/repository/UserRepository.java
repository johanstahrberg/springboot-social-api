package se.jensen.johan.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import se.jensen.johan.springboot.model.User;

import java.util.Optional;

/**
 * Repository for User entities.
 * Used to access user data from the database.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Checks if a user exists by username or email.
     *
     * @param username username to check
     * @param email    email to check
     * @return true if user exists
     */
    boolean existsByUsernameOrEmail(String username, String email);

    /**
     * Finds a user by username.
     *
     * @param username username of the user
     * @return user if found
     */
    Optional<User> findByUsername(String username);

    /**
     * Finds a user with posts by id.
     *
     * @param id id of the user
     * @return user with posts
     */
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.posts WHERE u.id = :id")
    Optional<User> findUserWithPosts(@Param("id") Long id);
}