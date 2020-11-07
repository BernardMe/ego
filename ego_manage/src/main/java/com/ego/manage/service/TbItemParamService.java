package com.ego.manage.service;


import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemDesc;
import com.ego.pojo.TbItemExample;
import com.ego.pojo.TbItemParam;
import com.github.pagehelper.PageInfo;

public interface TbItemParamService {

	/**
	 * 分页查询规格参数
	 * @param page
	 * @param rows
	 * @return
	 */
	EasyUIDataGrid showPage(int page, int rows);
	
	/**
	 * 批量根据id删除规格参数
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception 
	 */
	EgoResult delete(String ids);
	
	/**
	 * 根据catid查询规格参数
	 * @param catid
	 * @return
	 */
	EgoResult selectByCatid(long catid);
	
	/**
	 * 新增规格参数
	 * @param itemParam
	 * @return
	 * @throws Exception 
	 */
	EgoResult insItemParam(TbItemParam itemParam);
}
