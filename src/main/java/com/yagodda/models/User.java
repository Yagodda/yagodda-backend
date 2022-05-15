package com.yagodda.models;


import com.yagodda.models.enums.Role;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "users")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("User model")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, updatable = false)
    private Long id;

    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @ElementCollection(targetClass = Role.class)
    @CollectionTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();

    @Column(name = "password", length = 1000)
    private String password;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "username", unique = true)
    private String username;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "author")
    private List<Post> posts = new ArrayList<>();

    @Column(name = "created", updatable = false)
    private LocalDateTime created;

    @PrePersist
    private void onCreate() {
        created = LocalDateTime.now();
    }

    public void addPostToUser(Post post) {
        post.setAuthor(this);
        posts.add(post);
    }

    // security

    @Override
    public String getUsername() {
        return phoneNumber;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }
}
