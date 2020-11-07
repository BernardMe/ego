package com.ego.provider.service.imp;


import java.util.List;

import javax.annotation.Resource;

import com.ego.pojo.TbItemParamItem;
import com.ego.pojo.TbItemParamItemExample;
import com.ego.provider.mapper.TbItemParamItemMapper;
import com.ego.provider.service.DubboTbItemParamItemService;

public class DubboTbItemParamItemServiceImp implements DubboTbItemParamItemService {

	@Resource
	private TbItemParamItemMapper tbItemParamItemMapper;

	@Override
	public String showItemParam(long itemId) {
		//根据itemId 查询ItemParam
		TbItemParamItemExample example = new TbItemParamItemExample();
		example.createCriteria().andItemIdEqualTo(itemId);
		
		List<TbItemParamItem> list = tbItemParamItemMapper.selectByExampleWithBLOBs(example);
		if (list!=null) {
			return list.get(0).getParamData();
		}
		
		return "";
	}

	
}
