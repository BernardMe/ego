package com.ego.sso.service.imp;

import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EgoResult;
import com.ego.commons.utils.CookieUtils;
import com.ego.commons.utils.JsonUtils;
import com.ego.pojo.TbUser;
import com.ego.provider.service.DubboTbUserService;
import com.ego.redis.dao.JedisDao;
import com.ego.sso.service.TbUserService;

import redis.clients.jedis.Jedis;

@Service
public class TbUserServiceImp implements TbUserService {

	@Reference
	private DubboTbUserService dubboTbUserService;
	@Resource
	private JedisDao jedisDao;
	

	@Override
	public EgoResult selByUser(TbUser tbUser, HttpServletRequest request, HttpServletResponse response) {
		EgoResult er = new EgoResult();
		
		TbUser selectUser = dubboTbUserService.selByUser(tbUser);
		
		if (selectUser!=null) {
			// 生成UUID
			String key = UUID.randomUUID().toString();

			// 加入缓存Redis
			jedisDao.set(key, JsonUtils.objectToJson(selectUser));
			//设置过期时间
			jedisDao.expire(key, 60 * 60 * 24 * 7);

			// 设置Cookie
			CookieUtils.setCookie(request, response, "eToken", key, 60 * 60 * 24 * 7);
			
			er.setStatus(200);
			er.setData("OK");
		} else {
			er.setData("用户名密码错误!");
		}
		
		return er;
	}

	@Override
	public Object selUserInfoByToken(String token) {
		EgoResult er = new EgoResult();
		
		//只会从Redis缓存中获取token对应字符串
		String value = jedisDao.get(token);
		if (value!=null && !value.equals("")) {
			TbUser user = JsonUtils.jsonToPojo(value, TbUser.class);
			
			er.setStatus(200);
			er.setData(user);
			return er;
		} else {
			er.setMsg("获取失败");
		}
		
		return er;
	}

}
