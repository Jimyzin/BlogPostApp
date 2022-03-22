package com.assessment.BlogPostApp.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Post {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postId;
    private String title;
    private String content;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId", nullable = false)
    private User user;

    @CreationTimestamp
    // @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime creationTimestamp;

    @UpdateTimestamp
    // @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime lastUpdatedTimestamp;
}
