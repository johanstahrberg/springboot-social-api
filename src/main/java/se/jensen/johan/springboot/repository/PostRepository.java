package se.jensen.johan.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.jensen.johan.springboot.model.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    
    List<Post> findAllByOrderByCreatedAtDesc();


    List<Post> findByUserIdOrderByCreatedAtDesc(Long userId);
}