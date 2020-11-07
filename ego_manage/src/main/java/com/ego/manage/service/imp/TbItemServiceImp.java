package com.ego.manage.service.imp;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.utils.HttpClientUtil;
import com.ego.commons.utils.IDUtils;
import com.ego.commons.utils.JsonUtils;
import com.ego.manage.service.TbItemService;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemDesc;
import com.ego.pojo.TbItemParamItem;
import com.ego.provider.service.DubboTbItemService;
import com.ego.redis.dao.JedisDao;


@Service
public class TbItemServiceImp implements TbItemService {

	@Reference
	private DubboTbItemService dubboTbItemService;
	@Value("${search.url}")
	private String url;
	@Value("${REDIS_ITEM_ITEMDETAIL}")
	private String item_itemDetail;
	@Resource
	private JedisDao jedisDao;
	
	@Override
	public EasyUIDataGrid show(int page, int rows) {
		
		return dubboTbItemService.show(page, rows);
	}

	@Override
	public int update(String ids, byte status) {

		//获取String型数组
		String[] idArr = ids.split(",");
		int index = 0;
		
		//循环遍历 获取每一个item并 设置status
		for(String idStr : idArr){
			TbItem tbItem = new TbItem();
			
			tbItem.setId(Long.parseLong(idStr));
			tbItem.setStatus(status);
			
			//商品下架(status=2)和删除(status=3)都要删除Redis中缓存
			if (status==2 || status==3) {
				jedisDao.del(item_itemDetail+idStr);
			}
			
			index += dubboTbItemService.updItemStatus(tbItem);	
		}
		
		//判断index
		if (index == idArr.length){
			//更新成功
			return 1;
		}
		//更新失败
		return 0;
	}

	@Override
	public int insItem(TbItem item, String desc, String itemParams) throws Exception {
		//生产Long id
		long id = IDUtils.genItemId();
		Date date = new Date();
		//填充对象
		item.setId(id);
		item.setStatus((byte) 1);
		item.setCreated(date);
		item.setUpdated(date);
		
		TbItemDesc itemDesc = new TbItemDesc();
		itemDesc.setItemId(id);
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(date);
		itemDesc.setUpdated(date);
		
		TbItemParamItem iparamItem = new TbItemParamItem();
		iparamItem.setItemId(id);
		iparamItem.setParamData(itemParams);
		iparamItem.setCreated(date);
		iparamItem.setUpdated(date);
		
		int index = dubboTbItemService.insItem(item, itemDesc, iparamItem);
		
		//出于效率考虑 子线程发送请求到search模块
		final TbItem finalItem = item;
		final String finalDesc = desc;
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// run方法
				Map<String, Object> map = new HashMap<>();
				map.put("item", finalItem);
				map.put("desc", finalDesc);
				System.out.println(url);
				HttpClientUtil.doPostJson(url, JsonUtils.objectToJson(map));
			}
		}).start();
		
		
		if(index == 1){
			return 1;
		} else {
			return 0;
		}
	}
}
