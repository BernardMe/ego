package com.ego.provider.service;


import java.util.List;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.pojo.TbContent;

public interface DubboTbContentService {

	/**
	 * 内容分页查询
	 * @param page
	 * @param rows
	 * @return
	 */
	EasyUIDataGrid show(long categoryId, int page, int rows);
	
	/**
	 * 根据id修改内容
	 * @param id
	 * @return
	 */
	int updContent(TbContent tbContent);
	
	/**
	 * 新增内容
	 * @param tbContent
	 * @return
	 * @throws Exception 
	 */
	int insContent(TbContent content);
	
	/**
	 * 根据count查询
	 * @param count
	 * @param bySort
	 * @return
	 * @throws Exception 
	 */
	List<TbContent> selByCount(int count, boolean bySort);
}
