package com.suhanlee.luckybikideffenceapiserver.util;

import com.suhanlee.luckybikideffenceapiserver.config.error.ErrorCode;
import com.suhanlee.luckybikideffenceapiserver.config.error.exception.RestException;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedisLockUtil {

    private final RedissonClient redissonClient;

    private final String LOCK_KEY = "redis:lock:";
    private final int WAIT_TIME = 10;
    private final int LEASE_TIME = 3;

    public RLock getLock(long userId){
        RLock lock = redissonClient.getLock(LOCK_KEY + userId);
        try{
            if(lock.tryLock(WAIT_TIME, LEASE_TIME, TimeUnit.SECONDS)){
                return lock;
            }
        } catch (InterruptedException e) {
            throw new RestException(ErrorCode.Lock_FAILED);
        } finally {
            lock.unlock();
        }
        return null;
    }

    public void unLock(RLock lock){
        if(lock.isLocked()){
            lock.unlock();
        }
    }
}
