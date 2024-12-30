package com.suhanlee.luckybikideffenceapiserver.currency.repository;

import com.suhanlee.luckybikideffenceapiserver.currency.model.Diamond;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiamondRepository extends JpaRepository<Diamond, Long> {
    Optional<Diamond> findByUserId(Long userId);
}
