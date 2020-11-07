package com.ego.portal.service.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.utils.JsonUtils;
import com.ego.pojo.TbContent;
import com.ego.portal.service.TbContentService;
import com.ego.provider.service.DubboTbContentService;
import com.ego.redis.dao.JedisDao;

@Service
public class TbContentServiceImp implements TbContentService {

	@Reference
	private DubboTbContentService dubboTbContentService;
	@Resource
	private JedisDao jedisDao;
	@Value("${redis.bigpic.key}")
	private String key;
	
	@Override
	public String showAd(int count, boolean bySort) {
		//先检查redis中有没有
		System.out.println(jedisDao.exists(key));
		
		//如果redis已存在，直接返回
		if (jedisDao.exists(key)) {
			String value = jedisDao.get(key);
			if(value!=null && !value.equals("")) {
				return jedisDao.get(key);
			}
		}
	
		//如果redis中没有，从MySQL中查询
		List<TbContent> list = dubboTbContentService.selByCount(count, bySort);
		
		//包装成Map类型的list集合
		List<Map<String, Object>> mapList = new ArrayList<>();
		for(TbContent ct : list) {
			Map<String, Object> m = new HashMap<>();
			m.put("srcB", ct.getPic2());
			m.put("height", 240);
			m.put("alt", "");
			m.put("width", 670);
			m.put("src", ct.getPic());
			m.put("widthB", 550);
			m.put("href", ct.getUrl());
			m.put("heightB", 240);
			mapList.add(m);
		}
		
		
		//查到后，并存入redis
		String list4redis = JsonUtils.objectToJson(mapList);
		jedisDao.set(key, list4redis);
		
		return list4redis;
	}
	
	

}
