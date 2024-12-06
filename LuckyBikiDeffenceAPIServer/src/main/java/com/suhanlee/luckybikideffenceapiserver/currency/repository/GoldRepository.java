package com.suhanlee.luckybikideffenceapiserver.currency.repository;

import com.suhanlee.luckybikideffenceapiserver.currency.model.Gold;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GoldRepository extends JpaRepository<Gold, Long> {
    Optional<Gold> findByUserId(Long userId);
}
