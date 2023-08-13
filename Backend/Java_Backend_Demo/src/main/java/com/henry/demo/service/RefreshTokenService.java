package com.henry.demo.service;

import java.util.Optional;

import com.henry.demo.domain.RefreshToken;

public interface RefreshTokenService {
	public RefreshToken createRefreshToken(Integer userId);
	public Optional<RefreshToken> findByToken(String token);
	public RefreshToken verifyExpiration(RefreshToken token);
	public int deleteByUserId(Integer userId);
}
