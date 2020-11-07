package com.ego.item.service.imp;


import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.TbItemChild;
import com.ego.commons.utils.JsonUtils;
import com.ego.item.service.TbItemDescService;
import com.ego.pojo.TbItemDesc;
import com.ego.provider.service.DubboTbItemDescService;
import com.ego.redis.dao.JedisDao;

@Service
public class TbItemDescServiceImp implements TbItemDescService {

	@Reference
	private DubboTbItemDescService dubboTbItemDescService;
	@Resource
	private JedisDao jedisDao;
	@Value("${REDIS_ITEM_ITEMDESC}")
	private String item_itemDesc;

	@Override
	public String selById(long id) {
		String itemDescStr = jedisDao.get(item_itemDesc+id);
		//TODO 判断Redis缓存是否存在
		if (itemDescStr!=null && !itemDescStr.equals("")) {
			return itemDescStr;
		}
		// 
		String itemDesc = dubboTbItemDescService.selById(id).getItemDesc();
		
		//存入Redis缓存
		jedisDao.set(item_itemDesc+id, itemDesc);
				
		return itemDesc;
	}
}
