package com.ego.manage.service;


import java.util.List;

import com.ego.commons.pojo.EasyUITree;
import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbContentCategory;

public interface TbContentCatService {

	/**
	 * 根据父内容分类id显示其子内容分类
	 * @param id
	 * @return
	 */
	List<EasyUITree> showChildren(long id);

	/**
	 * 新增内容类目
	 * @param contentCat
	 * @return
	 */
	EgoResult create(TbContentCategory contentCat);
	
	/**
	 * 修改内容类目
	 * @param contentCat
	 * @return
	 */
	EgoResult update(TbContentCategory contentCat);
	
	/**
	 * 删除内容类目
	 * @param contentCat
	 * @return
	 */
	EgoResult delete(TbContentCategory contentCat);
	
}
