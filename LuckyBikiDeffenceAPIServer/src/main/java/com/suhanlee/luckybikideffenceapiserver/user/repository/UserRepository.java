package com.suhanlee.luckybikideffenceapiserver.user.repository;

import com.suhanlee.luckybikideffenceapiserver.user.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
}
