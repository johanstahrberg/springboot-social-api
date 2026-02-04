package se.jensen.johan.springboot.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * Entity class for posts.
 * Represents a post created by a user.
 */
@Entity
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String text;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Empty constructor.
     */
    public Post() {
    }

    /**
     * Creates a new post.
     *
     * @param id        id of the post
     * @param text      text of the post
     * @param createdAt time when the post was created
     * @param user      user who created the post
     */
    public Post(Long id, String text, LocalDateTime createdAt, User user) {
        this.id = id;
        this.text = text;
        this.createdAt = createdAt;
        this.user = user;
    }

    /**
     * Returns the id of the post.
     *
     * @return id of the post
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the id of the post.
     *
     * @param id id of the post
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the text of the post.
     *
     * @return text of the post
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the text of the post.
     *
     * @param text text of the post
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Returns the creation time of the post.
     *
     * @return creation time
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the creation time of the post.
     *
     * @param createdAt creation time
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Returns the user who created the post.
     *
     * @return user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user who created the post.
     *
     * @param user user
     */
    public void setUser(User user) {
        this.user = user;
    }
}