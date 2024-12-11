package com.suhanlee.luckybikideffenceapiserver.currency.repository;

import com.suhanlee.luckybikideffenceapiserver.currency.model.GoldRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GoldRecordRepository extends JpaRepository<GoldRecord, Long> {
    Optional<GoldRecord> findGoldRecordByIdempotentKey(String idempotentKey);
}
