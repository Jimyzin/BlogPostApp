package com.assessment.BlogPostApp.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class User {

    @Id
    private String username;
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Post> posts;

    @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
    private List<Authority> authorities;
}
