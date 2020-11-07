package com.ego.item.service.imp;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.TbItemChild;
import com.ego.commons.utils.JsonUtils;
import com.ego.item.pojo.ParamGroup;
import com.ego.item.pojo.ParamNode;
import com.ego.item.service.TbItemDescService;
import com.ego.item.service.TbItemParamItemService;
import com.ego.pojo.TbItemDesc;
import com.ego.pojo.TbItemParamItem;
import com.ego.provider.service.DubboTbItemDescService;
import com.ego.provider.service.DubboTbItemParamItemService;
import com.ego.redis.dao.JedisDao;

@Service
public class TbItemParamItemServiceImp implements TbItemParamItemService {

	@Reference
	private DubboTbItemParamItemService dubboTbItemParamItemService;
	@Resource
	private JedisDao jedisDao;

	@Override
	public String selParamById(long id) {
		
		String itemParam = dubboTbItemParamItemService.showItemParam(id);
		
		List<ParamGroup> list = JsonUtils.jsonToList(itemParam, ParamGroup.class);
		//拼接表格
		StringBuffer result = new StringBuffer("<div width=\"500px;\" >");
		// 遍历list
		for (ParamGroup paramGroup : list) {
			result.append("<div style=\"border-top:gray solid 1px; padding: 12px 0; line-height: 220%; color: #999; font-size: 12px;\">");
			result.append("<h3 >"+paramGroup.getGroup()+"<h3/>");
			
			List<ParamNode> nodes = paramGroup.getParams();
			for(int i=0; i<nodes.size(); i++){
				
					result.append("<dl style=\"margin-left: 110px;\" >");
					result.append("<dt style=\"width: 100px; float:left; text-align:right; padding-right:5px;\" >"+nodes.get(i).getK()+"</dt>");
					result.append("<dd style=\"margin-left: 210px;\" >"+nodes.get(i).getV()+"</dd></dl>");
				
			}
			result.append("</div>");
		}
		
		result.append("</div>");
		
		return result.toString();
	}
}
