package com.assessment.BlogPostApp.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Authority implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer authorityId;
    private String authority;

    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(name = "user",
            joinColumns = @JoinColumn(name = "authorityId"),
            inverseJoinColumns = @JoinColumn(name = "username"))
    private List<User> users;
}
