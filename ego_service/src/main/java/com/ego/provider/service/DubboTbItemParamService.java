package com.ego.provider.service;



import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.pojo.TbItemParam;

public interface DubboTbItemParamService {


	/**
	 * 分页查询规格参数
	 * @param page
	 * @param rows
	 * @return
	 */
	EasyUIDataGrid showPage(int page, int rows);
	
	/**
	 * 根据id删除规格参数
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception 
	 */
	int deleteById(long id) throws Exception;
	
	/**
	 * 根据catid查询规格参数
	 * @param catid
	 * @return
	 */
	TbItemParam selectByCatid(long catid);
	
	/**
	 * 新增规格参数
	 * @param itemParam
	 * @return
	 * @throws Exception 
	 */
	int insItemParam(TbItemParam itemParam) throws Exception;
}
