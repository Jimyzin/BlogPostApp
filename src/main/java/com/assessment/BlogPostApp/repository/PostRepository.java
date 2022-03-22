package com.assessment.BlogPostApp.repository;

import com.assessment.BlogPostApp.entity.Post;
import com.assessment.BlogPostApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    Optional<Post> findByPostIdAndUser(Integer postId, User user);

    Optional<Post> findByPostId(Integer postId);

    List<Post> findAllByUser(User user);
}
