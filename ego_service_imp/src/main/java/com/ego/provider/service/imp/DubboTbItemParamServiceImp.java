package com.ego.provider.service.imp;

import java.util.List;

import javax.annotation.Resource;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.pojo.TbItemParam;
import com.ego.pojo.TbItemParamExample;
import com.ego.provider.mapper.TbItemParamMapper;
import com.ego.provider.service.DubboTbItemParamService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

public class DubboTbItemParamServiceImp implements DubboTbItemParamService {

	@Resource
	private TbItemParamMapper tbItemParamMapper;

	/**
	 * 分页查询规格参数
	 * @param page
	 * @param rows
	 * @return
	 */
	public EasyUIDataGrid showPage(int page, int rows){
		//设置起始记录
		PageHelper.startPage(page, rows);
		
		//查询全部
		List<TbItemParam> list = tbItemParamMapper.selectByExampleWithBLOBs(new TbItemParamExample());

		//分页代码
		//设置分页条件
		PageInfo<TbItemParam> pi = new PageInfo<>(list);
		
		//放入到实体类
		EasyUIDataGrid datagrid = new EasyUIDataGrid();
		datagrid.setRows(pi.getList());
		datagrid.setTotal(pi.getTotal());
	
		return datagrid;
	}
	
	/**
	 * 根据id删除规格参数
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	public int deleteById(long id) throws Exception{
		int index;
		try {
			index = tbItemParamMapper.deleteByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("删除失败，数据回滚");
		}
		return index;
	}

	/**
	 * 根据catid查询规格参数
	 * @param catid
	 * @return
	 */
	@Override
	public TbItemParam selectByCatid(long catid) {		
		TbItemParamExample example = new TbItemParamExample();
		example.createCriteria().andItemCatIdEqualTo(catid);
		List<TbItemParam> list = tbItemParamMapper.selectByExampleWithBLOBs(example);

		if (list != null && list.size()>0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public int insItemParam(TbItemParam itemParam) throws Exception {

		int index = 0;
		try {
			index = tbItemParamMapper.insert(itemParam);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("新增规格参数失败!");
		}
		return index;
	}
}
