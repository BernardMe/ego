package com.ego.provider.service;


import java.util.List;

import com.ego.pojo.TbUser;

public interface DubboTbUserService {

	
	/**
	 * 根据tbUser查询
	 * @param tbUser
	 * @return
	 */
	TbUser selByUser(TbUser tbUser);
}
