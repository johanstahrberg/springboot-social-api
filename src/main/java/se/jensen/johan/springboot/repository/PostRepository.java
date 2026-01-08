package se.jensen.johan.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.jensen.johan.springboot.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}