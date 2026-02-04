package se.jensen.johan.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.jensen.johan.springboot.model.Post;

import java.util.List;

/**
 * Repository for Post entities.
 * Used to access post data from the database.
 */
public interface PostRepository extends JpaRepository<Post, Long> {

    /**
     * Returns all posts sorted by creation time.
     *
     * @return list of posts
     */
    List<Post> findAllByOrderByCreatedAtDesc();

    /**
     * Returns all posts for a user sorted by creation time.
     *
     * @param userId id of the user
     * @return list of posts
     */
    List<Post> findByUserIdOrderByCreatedAtDesc(Long userId);
}