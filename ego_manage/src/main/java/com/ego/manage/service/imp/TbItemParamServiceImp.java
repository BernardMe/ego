package com.ego.manage.service.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.manage.pojo.TbItemParamChild;
import com.ego.manage.service.TbItemParamService;
import com.ego.pojo.TbItemCat;
import com.ego.pojo.TbItemParam;
import com.ego.provider.service.DubboTbItemCatService;
import com.ego.provider.service.DubboTbItemParamService;

@Service
public class TbItemParamServiceImp implements TbItemParamService {

	@Reference
	private DubboTbItemParamService dubboTbItemParamService;
	@Reference
	private DubboTbItemCatService dubboTbItemCatService;

	@Override
	public EasyUIDataGrid showPage(int page, int rows) { 
		//获取原始datagrid
		EasyUIDataGrid datagrid = dubboTbItemParamService.showPage(page, rows);
		
		
		// EasyUIDataGrid中遍历加入 商品类目名称
		List<TbItemParam> list = (List<TbItemParam>) datagrid.getRows();
		List<TbItemParamChild> list2 = new ArrayList<TbItemParamChild>();

		for (TbItemParam item : list) {
			
			TbItemParamChild child = new TbItemParamChild();
			
			Date date = new Date();
			
			child.setId(item.getId());
			//加入 商品类目名称
			child.setItemCatId(item.getItemCatId());
			child.setItemCatName(dubboTbItemCatService.selByCid(item.getItemCatId()).getName());
			child.setParamData(item.getParamData());
			child.setCreated(date);
			child.setUpdated(date);

			list2.add(child);
		}
		
		datagrid.setRows(list2);

		return datagrid;
	}

	@Override
	public EgoResult delete(String ids) {
		EgoResult result = new EgoResult();
		int index = 0;
		
		String[] idArr = ids.split(",");
		try {
			for(String idstr : idArr){
				index += dubboTbItemParamService.deleteById(Long.parseLong(idstr));
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			//e.printStackTrace();
			result.setData(e.getMessage());
		}
		if(index == idArr.length){
			result.setStatus(200);
		}
		return result;
	}

	@Override
	public EgoResult selectByCatid(long catid) {
		EgoResult result = new EgoResult();
		TbItemParam itemParam = dubboTbItemParamService.selectByCatid(catid);
		if (itemParam != null) {
			result.setStatus(200);
			result.setData(itemParam);
		}
		return result;
	}

	@Override
	public EgoResult insItemParam(TbItemParam itemParam) {
		EgoResult result = new EgoResult();
		int index = 0;
		try {
			index = dubboTbItemParamService.insItemParam(itemParam);
		} catch (Exception e) {
			//e.printStackTrace();
			result.setData(e.getMessage());
		}
		if(index == 1){
			result.setStatus(200);
		}
		return result;
	}
}
