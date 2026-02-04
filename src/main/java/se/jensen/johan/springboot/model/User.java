package se.jensen.johan.springboot.model;

import jakarta.persistence.*;

import java.util.List;

/**
 * Entity class for users.
 * Represents a user in the application.
 */
@Entity
@Table(name = "app_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;

    @Column(name = "display_name", nullable = false)
    private String displayName;

    @Column(nullable = false)
    private String bio;

    @Column(name = "profile_image_path")
    private String profileImagePath;

    @OneToMany(mappedBy = "user",
            cascade = CascadeType.REMOVE,
            orphanRemoval = true)
    private List<Post> posts;

    /**
     * Empty constructor.
     */
    public User() {
    }

    /**
     * Creates a new user.
     *
     * @param id               id of the user
     * @param username         username of the user
     * @param email            email address of the user
     * @param password         password of the user
     * @param role             role of the user
     * @param displayName      display name of the user
     * @param bio              bio text of the user
     * @param profileImagePath path to profile image
     */
    public User(Long id, String username, String email, String password,
                String role, String displayName, String bio, String profileImagePath) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.displayName = displayName;
        this.bio = bio;
        this.profileImagePath = profileImagePath;
    }

    /**
     * Returns the id of the user.
     *
     * @return id of the user
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the id of the user.
     *
     * @param id id of the user
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the username.
     *
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     *
     * @param username username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the email address.
     *
     * @return email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address.
     *
     * @param email email address
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the password.
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     *
     * @param password password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the role of the user.
     *
     * @return role
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the role of the user.
     *
     * @param role role
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Returns the display name.
     *
     * @return display name
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Sets the display name.
     *
     * @param displayName display name
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Returns the bio text.
     *
     * @return bio text
     */
    public String getBio() {
        return bio;
    }

    /**
     * Sets the bio text.
     *
     * @param bio bio text
     */
    public void setBio(String bio) {
        this.bio = bio;
    }

    /**
     * Returns the profile image path.
     *
     * @return profile image path
     */
    public String getProfileImagePath() {
        return profileImagePath;
    }

    /**
     * Sets the profile image path.
     *
     * @param profileImagePath profile image path
     */
    public void setProfileImagePath(String profileImagePath) {
        this.profileImagePath = profileImagePath;
    }

    /**
     * Returns the posts created by the user.
     *
     * @return list of posts
     */
    public List<Post> getPosts() {
        return posts;
    }

    /**
     * Sets the posts for the user.
     *
     * @param posts list of posts
     */
    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}