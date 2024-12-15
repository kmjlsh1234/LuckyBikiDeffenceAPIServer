package com.suhanlee.luckybikideffenceapiserver.user.repository;

import com.suhanlee.luckybikideffenceapiserver.user.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByUserId(long userId);

    Optional<Users> findByEmail(String email);
}
