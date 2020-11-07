package com.ego.manage.service;



import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbContent;

public interface TbContentService {

	/**
	 * 内容分页查询
	 * @param page
	 * @param rows
	 * @return
	 */
	EasyUIDataGrid show(long categoryId, int page, int rows);

	/**
	 * 修改内容
	 * @param contentCat
	 * @return
	 */
	EgoResult update(TbContent content);
	
	/**
	 * 新增内容
	 * @param contentCat
	 * @return
	 */
	int insert(TbContent content);
	
}
