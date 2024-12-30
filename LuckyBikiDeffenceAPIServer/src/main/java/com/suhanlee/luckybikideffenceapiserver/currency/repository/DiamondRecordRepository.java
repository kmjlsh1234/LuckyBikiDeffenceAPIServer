package com.suhanlee.luckybikideffenceapiserver.currency.repository;

import com.suhanlee.luckybikideffenceapiserver.currency.model.DiamondRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiamondRecordRepository extends JpaRepository<DiamondRecord, Long> {
    Optional<DiamondRecord> findDiamondRecordByIdempotentKey(String idempotentKey);
}
