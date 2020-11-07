package com.ego.manage.service.imp;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.commons.utils.JsonUtils;
import com.ego.manage.service.TbContentService;
import com.ego.pojo.TbContent;
import com.ego.provider.service.DubboTbContentService;
import com.ego.redis.dao.JedisDao;

import redis.clients.jedis.JedisCluster;

@Service
public class TbContentServiceImp implements TbContentService {

	@Reference
	private DubboTbContentService dubboTbContentService;
	@Resource
	private JedisDao jedisDao;
	@Value("${redis.bigpic.key}")
	private String key;

	@Override
	public EasyUIDataGrid show(long categoryId, int page, int rows){

		return dubboTbContentService.show(categoryId, page, rows);
	}

	@Override
	public EgoResult update(TbContent content) {
		EgoResult er = new EgoResult();
		
		Date date = new Date();
		content.setUpdated(date);
		int index = dubboTbContentService.updContent(content);
		
		//判断redis 如果bigpic已缓存，更新bigpic；未缓存则nothing
		
		
		if (index > 0) {
			er.setStatus(200);
		} else {
			er.setData("更新失败!");
		}
		
		return er;
	}

	@Override
	public int insert(TbContent content) {
		Date date = new Date();
		
		content.setCreated(date);
		content.setUpdated(date);
		int index = dubboTbContentService.insContent(content);
		
		//判断redis 如果bigpic已缓存，更新bigpic；未缓存则nothing
		if (content.getCategoryId()==89 && jedisDao.exists(key)) {
			String value = jedisDao.get(key);
			if (value!=null && !"".equals(value)) {
				HashMap<String, Object> m = new HashMap<>();
				List<HashMap> mapList = JsonUtils.jsonToList(value, HashMap.class);;
				
				m.put("srcB", content.getPic2());
				m.put("height", 240);
				m.put("alt", "");
				m.put("width", 670);
				m.put("src", content.getPic());
				m.put("widthB", 550);
				m.put("href", content.getUrl());
				m.put("heightB", 240);
				//mapList.add(m);
				if (mapList.size() == 6) {
					mapList.remove(5);
 				}
				//插入第一个元素
				mapList.add(0, m);
				System.out.println(mapList.get(0).toString());
				
				//更新bigpic
				String list4redis = JsonUtils.objectToJson(mapList);
				jedisDao.set(key, list4redis);
			}
		}
		
		return index;
	}


}
