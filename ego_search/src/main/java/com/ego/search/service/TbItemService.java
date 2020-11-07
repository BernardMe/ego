package com.ego.search.service;

import java.io.IOException;
import java.util.Map;

import org.apache.solr.client.solrj.SolrServerException;

public interface TbItemService {

	/**
	 * 初始化Solr
	 * @return
	 * @throws IOException 
	 * @throws SolrServerException 
	 */
	void init() throws SolrServerException, IOException;
	
	/**
	 * 根据item_title搜索
	 * @param q
	 * @param page
	 * @param rows
	 * @return
	 * @throws IOException 
	 * @throws SolrServerException 
	 */
	Map<String, Object> selByQuery(String q, int page, int rows) throws SolrServerException, IOException;
	
	/**
	 * 根据map，desc更新Solr数据
	 * @param map
	 * @param desc
	 * @return
	 * @throws IOException 
	 * @throws SolrServerException 
	 */
	int upd4Map(Map<String, Object> map, String desc) throws SolrServerException, IOException;
}
