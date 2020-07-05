package com.lin.oos.search.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.lin.oos.pojo.PmsProductItem;
import com.lin.oos.service.PmsProductItemService;
import com.lin.oos.service.SearchService;
import com.lin.oos.vo.OosResult;
import com.lin.oos.vo.SearchResult;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@com.alibaba.dubbo.config.annotation.Service(interfaceClass = SearchService.class)
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SolrClient solrClient;

    @Reference
    private PmsProductItemService pmsProductItemService;



    @Override
    public List<PmsProductItem> getAllData() {
        return pmsProductItemService.getAllData();
    }

    @Override
    public OosResult deleteAll() {


        try {
            solrClient.deleteByQuery("*:*");


            solrClient.commit();
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
        }
        return OosResult.ok();
    }

    @Override
    public OosResult addItem(PmsProductItem productItem) {


        try {

            solrClient.addBean(productItem);


            solrClient.commit();


        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
        }
        return OosResult.ok();
    }

    @Override
    public OosResult addItems() {


        try {

            List<PmsProductItem> productItems = getAllData();


            //批量增加
            solrClient.addBeans(productItems);

            solrClient.commit();


        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return OosResult.ok();
    }


    @Override
    public String findByExample(int pageNum, int pageSize, String keyword) {


        SearchResult searchResult = new SearchResult();


        List<PmsProductItem> pmsProductItems = null;

        try {
            //第一步：构建返回对象

            pmsProductItems = new ArrayList<PmsProductItem>();
            //第二步：查询索引库的数据
            SolrQuery solrQuery=this.getSolrQuery(pageNum,pageSize,keyword);

            QueryResponse query = solrClient.query(solrQuery);

            SolrDocumentList results = query.getResults();

            for (SolrDocument document : results) {

                PmsProductItem pmsProductItem = new PmsProductItem();

                pmsProductItem.setId(Integer.valueOf((String)document.get("id")));
                pmsProductItem.setTitle((String)document.get("title"));
                pmsProductItem.setImage((String)document.get("image"));
                pmsProductItem.setCid(((Long)document.get("cid")).intValue());
                pmsProductItem.setStatus((Integer) document.get("stauts"));
                pmsProductItem.setNum(((Long)document.get("cid")));
                pmsProductItem.setIngredients((String)document.get("ingredients"));
                pmsProductItem.setCategoryName((String)document.get("categoryName"));
                pmsProductItem.setPrice(Double.valueOf((String)document.get("price")));
                pmsProductItems.add(pmsProductItem);

            }


            searchResult.setList(pmsProductItems);
            searchResult.setPages((long)Math.ceil(results.getNumFound()/pageSize));
            searchResult.setPageNum((long)pageNum);
            searchResult.setNextPage((long)(pageNum+1));
            searchResult.setPrePage((long)(pageNum-1));
            searchResult.setPageSize((long)pageSize);
            searchResult.setCount(8l);//导航栏个数

            /*封装关键*/
            searchResult.setFirstPage((searchResult.getPages()-pageNum)<=((searchResult.getCount()-1L)/2)?searchResult.getPages()-searchResult.getCount()+1L:((pageNum-(searchResult.getCount()/2))<=0?1L:pageNum-(searchResult.getCount()/2)));
            searchResult.setLastPage(searchResult.getFirstPage()+searchResult.getCount()-1L);
            if (searchResult.getFirstPage() <= 0) {
                searchResult.setFirstPage(1L);
            }


        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
        }










        return JSON.toJSONString(searchResult);

    }




    private SolrQuery getSolrQuery(int pageNum, int pageSize, String keyword){
        //第一步：构建返回对象
        SolrQuery solrQuery=new SolrQuery();
        //第二步：拼接查询条件
        //拼接关键字
        if (keyword!=null&&!"".equals(keyword)) {
            solrQuery.set("q", keyword);
        }else {
            solrQuery.set("q", "*");
        }
        //拼接类目条件
        /*if (categoryName!=null&&!"".equals(categoryName)) {
            solrQuery.add("fq","item_category_name:"+ categoryName);
        }*/



        //分页
        int start=(pageNum-1)*pageSize;
        solrQuery.setStart(start);
        solrQuery.setRows(pageSize);


        //指定默认的查询字段
        solrQuery.set("df", "title");

        /*//配置支持高亮
        solrQuery.setHighlight(true);
        solrQuery.setHighlightSimplePre("<font style='color:red'>");
        solrQuery.setHighlightSimplePost("</font>");
        solrQuery.addHighlightField("item_title");*/



        //第三步：返回条件
        return solrQuery;
    }
}
