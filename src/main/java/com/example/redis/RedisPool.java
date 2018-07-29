package com.example.redis;

import java.util.Collections;

import redis.clients.jedis.Jedis;

public class RedisPool {

	/*
	set key value [EX seconds] [PX milliseconds] [NX|XX]
	
	if redis.call("get",KEYS[1]) == ARGV[1]
	then
	    return redis.call("del",KEYS[1])
	else
	    return 0
	end
	
	SET mykey "HI" EX 10
	SET mykey "HI" NX
	set mykey "hi" XX
	return 	OK     OR     nil

	*/
	
	
	private static final String LOCK_SUCCESS = "OK";
	private static final String SET_IF_NOT_EXIST = "NX";
	private static final String SET_WITH_EXPIRE_TIME = "0";
    
	private static final Long RELEASE_SUCCESS = 1L ;
	/**
	 * 尝试获取分布式锁
	 * @param jedis redis 客户端
	 * @param lockKey 锁
	 * @param requestId 请求标识
	 * @param expireTime 超时时间
	 * @return 是否获取成功
	 */
	public static boolean tryGetDistributeLock(Jedis jedis, String lockKey
			, String requestId, int expireTime ) {
		String result = jedis.set(lockKey, requestId, SET_IF_NOT_EXIST,SET_WITH_EXPIRE_TIME
				, expireTime);
		
		if (LOCK_SUCCESS.equals(result)) {
			return true;
		}
		return false;
		
	}
	
	public static boolean releaseDistributedLock(Jedis jedis, String lockKey, String requestId) {
		
		String script = "if redis.call('get',KEY[1]) == ARGV[1] then return redis.call('del' , KEY[1]) else return 0 end " ;
		Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));
		if(RELEASE_SUCCESS.equals(result)) {
			return true;
		}
		
		return false;
		
		
	}

	
	
	
	
	
	
	
	
}
