package com.ego.redis.dao.imp;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ego.redis.dao.JedisDao;

import redis.clients.jedis.JedisCluster;

@Repository
public class JedisDaoImp implements JedisDao{

	@Resource
	private JedisCluster jedisCluster;
	
	public boolean exists(String key){
		return jedisCluster.exists(key);
	}

	public String set(String key, String value){
		return jedisCluster.set(key, value);
	}
	
	public String get(String key){
		return jedisCluster.get(key);
	}
	
	public long del(String key){
		return jedisCluster.del(key);
	}
	
	public Long expire(String key, int seconds) {
		return jedisCluster.expire(key, seconds);
	}
}
