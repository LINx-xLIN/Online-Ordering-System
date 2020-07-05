package com.lin.oos.service;

import com.lin.oos.pojo.PmsProductItem;
import com.lin.oos.vo.OosResult;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;

import java.io.IOException;
import java.util.List;

public interface SearchService {

    /**
     * 采集数据
     * @return
     */
    List<PmsProductItem> getAllData();

    OosResult deleteAll();

    OosResult addItem(PmsProductItem productItem);
    OosResult addItems();




    String findByExample(int pageNum,int pageSize,String keyword);


}
