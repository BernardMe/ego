package com.ego.provider.service.imp;

import java.util.List;

import javax.annotation.Resource;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemDesc;
import com.ego.pojo.TbItemExample;
import com.ego.pojo.TbItemParamItem;
import com.ego.provider.mapper.TbItemDescMapper;
import com.ego.provider.mapper.TbItemMapper;
import com.ego.provider.mapper.TbItemParamItemMapper;
import com.ego.provider.service.DubboTbItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

public class DubboTbItemServiceImp implements DubboTbItemService {

	@Resource
	private TbItemMapper tbItemMapper;
	@Resource
	private TbItemDescMapper tbItemDescMapper;
	@Resource
	private TbItemParamItemMapper tbItemParamItemMapper;
	
	@Override
	public EasyUIDataGrid show(int page, int rows) {
		//设置起始记录
		PageHelper.startPage(page, rows);
		
		//查询全部
		List<TbItem> list = tbItemMapper.selectByExample(new TbItemExample());

		//分页代码
		//设置分页条件
		PageInfo<TbItem> pi = new PageInfo<>(list);
		
		//放入到实体类
		EasyUIDataGrid datagrid = new EasyUIDataGrid();
		datagrid.setRows(pi.getList());
		datagrid.setTotal(pi.getTotal());

		return datagrid;
	}

	@Override
	public int updItemStatus(TbItem tbItem) {
		
		return tbItemMapper.updateByPrimaryKeySelective(tbItem);
	}

	@Override
	public int insItem(TbItem item, TbItemDesc desc, TbItemParamItem paramItem) throws Exception {
		int index = 0;
		
		try {
			index = tbItemMapper.insertSelective(item);
			index += tbItemDescMapper.insertSelective(desc);
			index += tbItemParamItemMapper.insertSelective(paramItem);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("新增商品出现错误!");
		}
		
		if(index == 3){
			//新增成功
			return 1;
		}else {
			//新增失败
			return 0;
		}
		
	}

	@Override
	public List<TbItem> selByStatus(byte status) {
		//设置查询条件
		TbItemExample example = new TbItemExample();
		example.createCriteria().andStatusEqualTo(status);
		
		List<TbItem> list = tbItemMapper.selectByExample(example);
		
		return list;
	}

	@Override
	public TbItem selById(long id) {
		return tbItemMapper.selectByPrimaryKey(id);
	}
}
