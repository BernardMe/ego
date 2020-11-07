package com.ego.item.service.imp;




import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.TbItemChild;
import com.ego.commons.utils.JsonUtils;
import com.ego.item.service.TbItemService;
import com.ego.pojo.TbItem;
import com.ego.provider.service.DubboTbItemService;
import com.ego.redis.dao.JedisDao;

@Service
public class TbItemServiceImp implements TbItemService {

	@Reference
	private DubboTbItemService dubboTbItemService;
	@Resource
	private JedisDao jedisDao;
	@Value("${REDIS_ITEM_ITEMDETAIL}")
	private String item_itemDetail;

	@Override
	public TbItemChild selById(long id) {
		String itemDetailStr = jedisDao.get(item_itemDetail+id);
		//TODO 判断Redis缓存是否存在
		if (itemDetailStr!=null && !itemDetailStr.equals("")) {
			return JsonUtils.jsonToPojo(itemDetailStr, TbItemChild.class);
		}
		
		TbItemChild itemChild = new TbItemChild();
		
		// 填充TbItemChild
		TbItem item = dubboTbItemService.selById(id);
		
		itemChild.setId(item.getId());
		itemChild.setTitle(item.getTitle());
		itemChild.setSellPoint(item.getSellPoint());
		itemChild.setPrice(item.getPrice());
		//图片String处理
		itemChild.setImages(item.getPrice()!=null&&!item.getImage().equals("") ? item.getImage().split(","): new String[1]);
		
		//存入Redis缓存
		jedisDao.set(item_itemDetail+id, JsonUtils.objectToJson(itemChild));
		
		return itemChild;
	}
	
}
