package com.lin.oos.search.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lin.oos.pojo.PmsProductItem;
import com.lin.oos.service.SearchService;
import com.lin.oos.vo.OosResult;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class SearchController {

    @Reference
    private SearchService searchService;

   @GetMapping("/import/all")
    public OosResult createIndex(){
        try {
          return searchService.addItems();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return OosResult.build(400, "Solr创建索引库失败");
    }
    @GetMapping("/import/singleItem")
    public OosResult createSingleItem(PmsProductItem pmsProductItem){
        try {
          return searchService.addItem(pmsProductItem);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return OosResult.build(400, "Solr创建单个索引库失败");
    }

    @GetMapping("/delete/all")
    public OosResult deleteAll(){
        try {
          return searchService.deleteAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return OosResult.build(400, "Solr删除索引库失败");
    }

}
