package com.ego.search.controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.search.service.TbItemService;

@Controller
public class TbItemController {

	@Resource
	private TbItemService tbItemService;
	
	/**
	 * 初始化Solr
	 * @return
	 * @throws IOException 
	 * @throws SolrServerException 
	 */
	@RequestMapping(value="solr/init", produces="text/html; charset=utf-8")
	@ResponseBody
	public String init(){
		long start = System.currentTimeMillis();
		
		 try {
			tbItemService.init();
			
			long end = System.currentTimeMillis();
			
			return "Success ，用时"+ (end-start)/1000 +"秒";
			
		} catch (Exception e) {
			e.printStackTrace();
			return "初始化失败";
		}
	}
	
	/**
	 * 根据item_title搜索
	 * @param q
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value="search.html", produces="text/html; charset=utf-8")
	public String selByQuery(Model model, String q, @RequestParam(defaultValue="1") int page, @RequestParam(defaultValue="12")int rows) {
		
		try {
			q = new String(q.getBytes("ISO-8859-1"), "UTF-8"); 
			
			Map<String, Object> result = tbItemService.selByQuery(q, page, rows);
			
			model.addAttribute("query", result.get("query"));
			model.addAttribute("itemList", result.get("itemList"));
			model.addAttribute("totalPages", result.get("totalPages"));
			model.addAttribute("page", page);
		} catch (Exception e) {
			//e.printStackTrace();
			return "查询失败";
		}
		
		return "search";
	}
	 
	/**
	 * 根据map，desc更新Solr数据
	 * @param map
	 * @param desc
	 * @return
	 * @throws IOException 
	 * @throws SolrServerException 
	 */
	@RequestMapping(value="/solr/add", produces="text/html; charset=utf-8")
	@ResponseBody
	public int upd4Map(@RequestBody Map<String, Object> map) {
			
		try {
			return tbItemService.upd4Map((LinkedHashMap)map.get("item"), map.get("desc").toString());
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} 
		
	}
}
