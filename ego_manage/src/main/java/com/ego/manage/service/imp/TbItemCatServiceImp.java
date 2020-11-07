package com.ego.manage.service.imp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EasyUITree;
import com.ego.manage.service.TbItemCatService;
import com.ego.pojo.TbItemCat;
import com.ego.provider.service.DubboTbItemCatService;

@Service
public class TbItemCatServiceImp implements TbItemCatService {

	@Reference
	private DubboTbItemCatService dubboTbItemCatService;

	@Override
	public List<EasyUITree> showChildren(long id) {

		List<TbItemCat> list = dubboTbItemCatService.showChildren(id);
		// 转换为EasyUITree节点
		List<EasyUITree> treeList = new ArrayList<EasyUITree>();

		for (TbItemCat item : list) {
			EasyUITree node = new EasyUITree();
			node.setId(item.getId());
			node.setText(item.getName());
			node.setState(item.getIsParent() == true ? "closed" : "open");

			treeList.add(node);
		}

		return treeList;
	}

}
