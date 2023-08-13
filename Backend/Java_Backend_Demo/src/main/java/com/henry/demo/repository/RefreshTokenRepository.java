package com.henry.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.henry.demo.domain.RefreshToken;
import com.henry.demo.domain.User;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {
	Optional<RefreshToken> findByToken(String token);

	int deleteByUser(User user);

    Optional<RefreshToken> findByUser(User user);
}
