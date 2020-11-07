package com.ego.provider.service.imp;

import java.util.List;

import javax.annotation.Resource;

import com.ego.pojo.TbItemCat;
import com.ego.pojo.TbItemCatExample;
import com.ego.provider.mapper.TbItemCatMapper;
import com.ego.provider.service.DubboTbItemCatService;

public class DubboTbItemCatServiceImp implements DubboTbItemCatService {

	@Resource
	private TbItemCatMapper tbItemCatMapper;

	@Override
	public List<TbItemCat> showChildren(long pid) {
		
		TbItemCatExample example = new TbItemCatExample();
		example.createCriteria().andParentIdEqualTo(pid);
		
		 List<TbItemCat> list = tbItemCatMapper.selectByExample(example);
	
		 return list;
	}

	@Override
	public TbItemCat selByCid(long cid) {

		TbItemCat itemCat = tbItemCatMapper.selectByPrimaryKey(cid);
		return itemCat;
	}
	
	

}
