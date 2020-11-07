package com.ego.item.service.imp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.item.pojo.PortalMenu;
import com.ego.item.pojo.PortalMenuNode;
import com.ego.item.service.PortalMenuService;
import com.ego.pojo.TbItemCat;
import com.ego.provider.service.DubboTbItemCatService;

@Service
public class PortalMenuServiceImp implements PortalMenuService {

	@Reference
	private DubboTbItemCatService dubboTbItemCatService;
	
	@Override
	public PortalMenu showPortalMenu() {
		PortalMenu pm = new PortalMenu();
		
		//转换ItemCat到 PortalMenu
		List<Object> list = selAllMenu(dubboTbItemCatService.showChildren(0));
		pm.setData(list);

		return pm;
	}
	
	
	public List<Object> selAllMenu(List<TbItemCat> list) {
		
		List<Object> listNodes = new ArrayList<Object>();
		
		//如果第一次执行，则遍历一级分类list
		for(TbItemCat itemCat : list) {
			PortalMenuNode pmd = new PortalMenuNode();
			//如果是父节点
			if (itemCat.getIsParent()) {
				pmd.setU("/product/item/"+itemCat.getId()+".html");
				pmd.setN("<a href='/product/item/"+itemCat.getId()+".html'>"+itemCat.getName()+"</a>");
				pmd.setI(selAllMenu(dubboTbItemCatService.showChildren(itemCat.getId())));
				listNodes.add(pmd);
			} else { //如果是叶子节点
				String leaf = "/product/item/"+itemCat.getId()+".html|"+itemCat.getName();
				listNodes.add(leaf);
			}
			
		}	
		return listNodes;
	}

}
