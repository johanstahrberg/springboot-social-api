package se.jensen.johan.springboot.security;

import jakarta.annotation.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import se.jensen.johan.springboot.model.User;

import java.util.Collection;
import java.util.List;

/**
 * Custom UserDetails implementation.
 * Used by Spring Security to read user information.
 */
public class MyUserDetails implements UserDetails {

    private final User user;

    /**
     * Creates user details from a User entity.
     *
     * @param user user entity
     */
    public MyUserDetails(User user) {
        this.user = user;
    }

    /**
     * Returns the authorities for the user.
     *
     * @return user authorities
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
    }

    /**
     * Returns the password of the user.
     *
     * @return password
     */
    @Override
    public @Nullable String getPassword() {
        return user.getPassword();
    }

    /**
     * Returns the username.
     *
     * @return username
     */
    @Override
    public String getUsername() {
        return user.getUsername();
    }

    /**
     * Checks if the account is not expired.
     *
     * @return true if not expired
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Checks if the account is not locked.
     *
     * @return true if not locked
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Checks if credentials are not expired.
     *
     * @return true if not expired
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Checks if the account is enabled.
     *
     * @return true if enabled
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * Returns the domain user.
     *
     * @return user entity
     */
    public User getDomainUser() {
        return user;
    }
}