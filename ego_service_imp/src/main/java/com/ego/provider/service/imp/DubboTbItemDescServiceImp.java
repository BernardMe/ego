package com.ego.provider.service.imp;


import javax.annotation.Resource;

import com.ego.pojo.TbItemDesc;
import com.ego.provider.mapper.TbItemDescMapper;
import com.ego.provider.service.DubboTbItemDescService;

public class DubboTbItemDescServiceImp implements DubboTbItemDescService {

	@Resource
	private TbItemDescMapper tbItemDescMapper;

	@Override
	public TbItemDesc selById(long id) {
		return tbItemDescMapper.selectByPrimaryKey(id);
	}


}
