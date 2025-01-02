package com.suhanlee.luckybikideffenceapiserver.currency.repository;

import com.suhanlee.luckybikideffenceapiserver.currency.model.Energy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EnergyRepository extends JpaRepository<Energy, Long> {
    Optional<Energy> findByUserId(Long userId);
}
