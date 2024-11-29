package com.suhanlee.luckybikideffenceapiserver.user.repository;

import com.suhanlee.luckybikideffenceapiserver.user.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
}
