package com.suhanlee.luckybikideffenceapiserver.user.repository;

import com.suhanlee.luckybikideffenceapiserver.user.model.JwtRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JwtRecordRepository extends JpaRepository<JwtRecord, Long> {
}
