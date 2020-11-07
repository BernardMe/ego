package com.ego.sso.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbUser;

public interface TbUserService {

	
	/**
	 * SSO核心功能
	 * login
	 * @param tbUser
	 * @return
	 */
	EgoResult selByUser(TbUser tbUser, HttpServletRequest request, HttpServletResponse response);
	
	/**
	 * 根据token查询用户信息
	 * @param token
	 * @return
	 */
	Object selUserInfoByToken(String token);
}
