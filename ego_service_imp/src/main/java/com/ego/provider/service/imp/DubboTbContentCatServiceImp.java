package com.ego.provider.service.imp;

import java.util.List;

import javax.annotation.Resource;

import com.ego.pojo.TbContentCategory;
import com.ego.pojo.TbContentCategoryExample;
import com.ego.pojo.TbItemCat;
import com.ego.pojo.TbItemCatExample;
import com.ego.provider.mapper.TbContentCategoryMapper;
import com.ego.provider.mapper.TbItemCatMapper;
import com.ego.provider.service.DubboTbContentCatService;
import com.ego.provider.service.DubboTbItemCatService;

public class DubboTbContentCatServiceImp implements DubboTbContentCatService {

	@Resource
	private TbContentCategoryMapper tbContentCategoryMapper;

	@Override
	public List<TbContentCategory> showChildren(long pid) {
		
		TbContentCategoryExample example = new TbContentCategoryExample();
		example.createCriteria().andParentIdEqualTo(pid).andStatusEqualTo(1);
		
		 List<TbContentCategory> list = tbContentCategoryMapper.selectByExample(example);
	
		 return list;
	}

	@Override
	public TbContentCategory selById(long id) {

		TbContentCategory contentCat = tbContentCategoryMapper.selectByPrimaryKey(id);
		return contentCat;
	}

	@Override
	public int insContentCat(TbContentCategory contentCat) {
		//插入
		return tbContentCategoryMapper.insertSelective(contentCat);
	}

	@Override
	public int updContentCat(TbContentCategory contentCat) {
		
		return tbContentCategoryMapper.updateByPrimaryKeySelective(contentCat);
	}
	
}
