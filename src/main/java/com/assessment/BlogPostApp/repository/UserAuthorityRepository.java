package com.assessment.BlogPostApp.repository;

import com.assessment.BlogPostApp.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserAuthorityRepository extends JpaRepository<Authority, UUID> {
}
