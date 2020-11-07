package test.solr;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class TestSolrJ {

	private SolrClient solrClient = new HttpSolrClient("http://192.168.176.135:7080/solr");
	
	@Test
	public void insert() throws SolrServerException, IOException{
		
		SolrInputDocument doc = new SolrInputDocument();
		doc.setField("id", "150485081312088");
		doc.setField("item_title", "Dell游侠");
		doc.setField("item_sell_point", "Dell游侠. Happy Coding");
		doc.setField("item_price", "19900");
		doc.setField("item_category_name", "游戏本");
		doc.setField("item_image", "http://192.168.176.135/1504850805927121.jpg");
		doc.setField("item_desc", "Dell游侠. Happy Coding");
		solrClient.add(doc);
		solrClient.commit();
	}
	

	public void delete() throws SolrServerException, IOException{
		//HashMap<Object, Object> coll = new HashMap<>();
		UpdateResponse doc = solrClient.deleteById("110");
		System.out.println(doc);
		solrClient.commit();
	}
	

	public void highLight() throws SolrServerException, IOException{
		
		System.out.println("11111111111");
		
		//可视化左侧条
		SolrQuery param = new SolrQuery();
		//查询条件
		param.setQuery("ego1:*");
		//查询start rows
		param.setStart(0);
		//开启高亮
		param.setRows(10);

		
		//设置高亮数据列
		param.setHighlight(true);
		
		param.addHighlightField("ego1");
		//设置pre
		param.setHighlightSimplePre("<span style=\"color: red\">");
		//设置POst
		param.setHighlightSimplePost("</span>");
		//获取Response'
		QueryResponse queryResponse = solrClient.query(param);
		
		
		System.out.println("222222");
		//获取hh
		Map<String, Map<String, List<String>>> hh = queryResponse.getHighlighting();
		//获取result
		SolrDocumentList results = queryResponse.getResults();
		
		System.out.println("3333333333" + results.size());
		
		//打印为高亮result
		for (SolrDocument doc : results){
			System.out.println("44444444444");
	
	
			String id = (String) doc.getFieldValue("id");
			System.out.println("wei HighLighting"+doc.getFieldValue("ego1"));
			
			//打印高亮部分
			Map<String, List<String>> map = hh.get(id);
			//System.out.println("HightLED"+map.get("ego1"));
			
			List<String> list = map.get("ego1");
			System.out.println(list);
			if (list!=null && list.size()>0) {
				System.out.println("HightLED"+list.get(0));
			} else {
				System.out.println("没有 HightLED");
			}
			
		}
		
		
		
		
	}
}
