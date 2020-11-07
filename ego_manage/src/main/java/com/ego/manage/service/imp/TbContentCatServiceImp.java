package com.ego.manage.service.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EasyUITree;
import com.ego.commons.pojo.EgoResult;
import com.ego.commons.utils.IDUtils;
import com.ego.manage.service.TbContentCatService;
import com.ego.pojo.TbContentCategory;
import com.ego.provider.service.DubboTbContentCatService;

@Service
public class TbContentCatServiceImp implements TbContentCatService {

	@Reference
	private DubboTbContentCatService dubboTbContentCatService;

	@Override
	public List<EasyUITree> showChildren(long id) {

		List<TbContentCategory> list = dubboTbContentCatService.showChildren(id);
		// 转换为EasyUITree节点
		List<EasyUITree> treeList = new ArrayList<EasyUITree>();

		for (TbContentCategory contentCat : list) {
			EasyUITree node = new EasyUITree();
			node.setId(contentCat.getId());
			node.setText(contentCat.getName());
			node.setState(contentCat.getIsParent() == true ? "closed" : "open");

			treeList.add(node);
		}

		return treeList;
	}
	
	@Override
	public EgoResult create(TbContentCategory contentCat) {
		EgoResult er = new EgoResult();
		
		//检查是否已存在该内容类目名称
		List<TbContentCategory> children = dubboTbContentCatService.showChildren(contentCat.getParentId());
		for(TbContentCategory child : children) {
			if (contentCat.getName().equals(child.getName())) {
				er.setData("该内容类目名称已存在!");
			return er;
			}	
		}
		long id = IDUtils.genItemId();
		Date date = new Date();
		contentCat.setId(id);
		contentCat.setCreated(date);
		contentCat.setUpdated(date);
		contentCat.setIsParent(false);
		contentCat.setSortOrder(1);
		contentCat.setStatus(1);

		int index = dubboTbContentCatService.insContentCat(contentCat);
		
		//更新父节点IsParent属性
		if (index > 0) {
			TbContentCategory parent = new TbContentCategory();
			parent.setId(contentCat.getParentId());
			parent.setIsParent(true);
			if (dubboTbContentCatService.updContentCat(parent) > 0){
				er.setStatus(200);
				Map<String, Object> map = new HashMap<>();
				map.put("id", contentCat.getId());
				er.setData(map);
			}
		}
		return er;
		
	}

	@Override
	public EgoResult update(TbContentCategory contentCat) {
		EgoResult er = new EgoResult();
		
		//未传入parentId，主动获取parentId
		TbContentCategory cate = dubboTbContentCatService.selById(contentCat.getId());
		// 判断该内容类目的弟兄节点是否已存在改名称
		
		List<TbContentCategory> children = dubboTbContentCatService.showChildren(cate.getParentId());
		for(TbContentCategory child : children) {
			if (contentCat.getName().equals(child.getName())) {
				er.setData("该内容类目名称已存在!");
			return er;
			}	
		}
		int index = dubboTbContentCatService.updContentCat(contentCat);
		if (index > 0) {
			er.setStatus(200);
		}
		return er;
	}

	@Override
	public EgoResult delete(TbContentCategory contentCat) {
		EgoResult er = new EgoResult();
		// 逻辑删除
		contentCat.setStatus(2);
		int index = dubboTbContentCatService.updContentCat(contentCat);
		
		if (index > 0) {
			// 获取父节点id
			TbContentCategory curr = dubboTbContentCatService.selById(contentCat.getId());
			
			List<TbContentCategory> children = dubboTbContentCatService.showChildren(curr.getParentId());
			if (children==null || children.size()<=0) {
				//更新父节点isParent状态
				TbContentCategory parent = new TbContentCategory();
				parent.setId(curr.getParentId());
				parent.setIsParent(false);
				int rc = dubboTbContentCatService.updContentCat(parent);
				if (rc > 0) {
					er.setStatus(200);
				}
			}
			er.setStatus(200);
		}
		
		return er;
	}

}
