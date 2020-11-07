package com.ego.redis.dao;

public interface JedisDao {
	
	/**
	 * 是否存在值
	 */
	boolean exists(String key);

	/**
	 * 设置值
	 */
	String set(String key, String value);
	
	/**
	 * 获取值
	 */
	String get(String key);
	
	/**
	 * 删除值
	 */
	long del(String key);
	
	/**
	 * 设置有效期
	 */
	Long expire(String key, int seconds);
}
