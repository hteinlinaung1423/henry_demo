package com.henry.demo.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.henry.demo.domain.RefreshToken;
import com.henry.demo.exception.TokenRefreshException;
import com.henry.demo.repository.RefreshTokenRepository;
import com.henry.demo.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

	@Value("${jwt.refresh.expirationMs}")
	private Long refreshExpirationMs;

	@Autowired
	private RefreshTokenRepository refreshTokenRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public RefreshToken createRefreshToken(Integer userId) {

		RefreshToken rt = refreshTokenRepository.findByUser(userRepository.findById(userId).get()).orElse(null);
		if (rt == null) {
			RefreshToken refreshToken = new RefreshToken(userRepository.findById(userId).get(),
					Instant.now().plusMillis(refreshExpirationMs), UUID.randomUUID().toString());
			refreshToken = refreshTokenRepository.save(refreshToken);
			return refreshToken;
		} else {
			rt.setToken(UUID.randomUUID().toString());
			rt.setExpiryDate(Instant.now().plusMillis(refreshExpirationMs));
			refreshTokenRepository.save(rt);
			return rt;
		}
	}

	@Transactional
	@Override
	public Optional<RefreshToken> findByToken(String token) {
		return refreshTokenRepository.findByToken(token);
	}

	@Override
	public RefreshToken verifyExpiration(RefreshToken token) {
		if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
			refreshTokenRepository.delete(token);
			throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please login again!");
		}

		return token;
	}

	@Override
	public int deleteByUserId(Integer userId) {
		return refreshTokenRepository.deleteByUser(userRepository.findById(userId).get());
	}
}
