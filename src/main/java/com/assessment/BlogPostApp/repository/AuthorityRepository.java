package com.assessment.BlogPostApp.repository;

import com.assessment.BlogPostApp.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
}
