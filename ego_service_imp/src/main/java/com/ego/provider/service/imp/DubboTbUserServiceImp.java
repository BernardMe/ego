package com.ego.provider.service.imp;

import java.util.List;

import javax.annotation.Resource;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemDesc;
import com.ego.pojo.TbItemExample;
import com.ego.pojo.TbItemParamItem;
import com.ego.pojo.TbUser;
import com.ego.pojo.TbUserExample;
import com.ego.provider.mapper.TbItemDescMapper;
import com.ego.provider.mapper.TbItemMapper;
import com.ego.provider.mapper.TbItemParamItemMapper;
import com.ego.provider.mapper.TbUserMapper;
import com.ego.provider.service.DubboTbItemService;
import com.ego.provider.service.DubboTbUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

public class DubboTbUserServiceImp implements DubboTbUserService {
	
	@Resource
	private TbUserMapper tbUserMapper;
	
	@Override
	public TbUser selByUser(TbUser tbUser) {
		// 

		TbUserExample example = new TbUserExample();
		example.createCriteria().andUsernameEqualTo(tbUser.getUsername()).andPasswordEqualTo(tbUser.getPassword());
		List<TbUser> userList = tbUserMapper.selectByExample(example);
		
		if (userList!=null && userList.size()>0) {
			return userList.get(0);
		}
		return null;
	}
	
}
