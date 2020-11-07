package com.ego.provider.service.imp;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.pojo.TbContent;
import com.ego.pojo.TbContentExample;
import com.ego.provider.mapper.TbContentMapper;
import com.ego.provider.service.DubboTbContentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

public class DubboTbContentServiceImp implements DubboTbContentService {

	@Resource
	private TbContentMapper tbContentMapper;
	
	@Override
	public EasyUIDataGrid show(long categoryId, int page, int rows) {
		//设置起始记录
		PageHelper.startPage(page, rows);
		
		TbContentExample example = new TbContentExample();
		example.createCriteria().andCategoryIdEqualTo(categoryId);
		
		//查询全部
		List<TbContent> list = tbContentMapper.selectByExampleWithBLOBs(example);
		
		//分页代码
		//设置分页条件
		PageInfo<TbContent> pi = new PageInfo<>(list);
		
		//放入到实体类
		EasyUIDataGrid datagrid = new EasyUIDataGrid();
		datagrid.setRows(pi.getList());
		datagrid.setTotal(pi.getTotal());

		return datagrid;
	}

	@Override
	public int updContent (TbContent content) {
		
		return tbContentMapper.updateByPrimaryKeySelective(content);
	}

	@Override
	public List<TbContent> selByCount(int count, boolean bySort) {
	
		TbContentExample example = new TbContentExample();
		
		//按照update时间排序
		if(bySort) {
			example.setOrderByClause(" updated desc");
		}
		if (count != 0) {
			//设置起始记录
			PageHelper.startPage(1, count);
			
			List<TbContent> list = tbContentMapper.selectByExampleWithBLOBs(example);
			PageInfo<TbContent> pi = new PageInfo<>(list);
			
			return pi.getList();
		} else {
			return tbContentMapper.selectByExampleWithBLOBs(example);
		}
	}

	@Override
	public int insContent(TbContent content) {
		
		return tbContentMapper.insertSelective(content);
	}

}
