package com.ego.search.service.imp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemCat;
import com.ego.pojo.TbItemDesc;
import com.ego.provider.service.DubboTbItemCatService;
import com.ego.provider.service.DubboTbItemDescService;
import com.ego.provider.service.DubboTbItemService;
import com.ego.search.pojo.TbItemChild;
import com.ego.search.service.TbItemService;

@Service
public class TbItemServiceImp implements TbItemService {

	@Reference
	private DubboTbItemService dubboTbItemService;
	@Reference
	private DubboTbItemCatService dubboTbItemCatService;
	@Reference
	private DubboTbItemDescService dubboTbItemDescService;
	@Resource
	private CloudSolrClient solrClient;

	@Override
	public void init() throws SolrServerException, IOException {
		// 查询所有正常的商品 3100条数据
		List<TbItem> list = dubboTbItemService.selByStatus((byte) 1);

		for (TbItem item : list) {
			/*
			 * if(item.getId() == 974401){
			 * System.out.println(JsonUtils.objectToJson(item)); return; }
			 */

			// 商品对应的类目信息
			TbItemCat cat = dubboTbItemCatService.selByCid(item.getCid());
			// 商品对应的描述信息
			TbItemDesc desc = dubboTbItemDescService.selById(item.getId());
			// System.out.println(desc.getItemDesc());

			SolrInputDocument doc = new SolrInputDocument();

			doc.addField("id", item.getId());
			doc.addField("item_title", item.getTitle());
			doc.addField("item_sell_point", item.getSellPoint());
			doc.addField("item_price", item.getPrice());
			doc.addField("item_image", item.getImage());
			doc.addField("item_category_name", cat.getName());
			doc.addField("item_desc", desc.getItemDesc());
			doc.addField("item_updated", item.getUpdated());

			solrClient.add(doc);
		}
		// 提交
		solrClient.commit();

	}

	@Override
	public Map<String, Object> selByQuery(String q, int page, int rows) throws SolrServerException, IOException {
		Map<String, Object> resultMap = new HashMap<>();
		
		// 可视化左侧条
		SolrQuery param = new SolrQuery();
		// 查询条件
		param.setQuery("item_keywords:" + q);
		// 设置排序 DESC
		param.setSort("item_updated", ORDER.desc);
		// 设置start rows
		param.setStart(rows*(page-1));
		// 开启高亮
		param.setRows(rows);

		// 设置高亮数据列
		param.setHighlight(true);

		param.addHighlightField("item_title");
		// 设置pre
		param.setHighlightSimplePre("<span style=\"color: red\">");
		// 设置POst
		param.setHighlightSimplePost("</span>");
		// 获取Response'
		QueryResponse queryResponse = solrClient.query(param);

		// 获取未高亮 result
		SolrDocumentList results = queryResponse.getResults();
		// 获取高亮 hh
		Map<String, Map<String, List<String>>> hh = queryResponse.getHighlighting();
		
		List<TbItemChild> listChildren = new ArrayList<>();
		
		// 循环遍历
		for (SolrDocument doc : results) {
			TbItemChild child = new TbItemChild();
			
			//填充child
			child.setId(Long.parseLong(doc.getFieldValue("id").toString())); 
			child.setSellPoint(doc.getFieldValue("item_sell_point").toString());
			child.setPrice((Long) doc.getFieldValue("item_price"));
			//图片处理
			Object images = doc.getFieldValue("item_image");
			child.setImages(images==null||images.equals("") ? new String[1]: images.toString().split(","));

			// 打印高亮部分
			Map<String, List<String>> map = hh.get(doc.getFieldValue("id").toString());

			List<String> list = map.get("item_title");
			//判断list
			if (list != null && list.size() > 0) {
				child.setTitle(list.get(0));
			} else {
				child.setTitle(doc.getFieldValue("item_title").toString());
			}
			listChildren.add(child);
		}
		// 获取numFound
		long numFound = queryResponse.getResults().getNumFound();
		
		//设置resultMap
		resultMap.put("query", q);
		resultMap.put("itemList", listChildren);
		resultMap.put("totalPages", numFound%rows==0 ? numFound/rows : numFound/rows+1);

		return resultMap;
	}

	@Override
	public int upd4Map(Map<String, Object> map, String desc) throws SolrServerException, IOException {
		SolrInputDocument doc = new SolrInputDocument();
		
		
		//填充doc对象
		doc.setField("id", map.get("id"));
		doc.setField("item_title", map.get("title"));
		doc.setField("item_sell_point", map.get("sellPoint"));
	    doc.setField("item_price", map.get("price"));
	    doc.setField("item_image", map.get("image"));
	    doc.setField("item_category_name", dubboTbItemCatService.selByCid(Long.parseLong(map.get("cid").toString())).getName());
	    doc.setField("item_desc", desc);
		
		UpdateResponse response = solrClient.add(doc);
		solrClient.commit();
		
		if (response.getStatus() == 0) {
			System.out.println("11111");
			return 1;
		} else {
			System.out.println("00000");
			return 0;
		}
		
	}

}
