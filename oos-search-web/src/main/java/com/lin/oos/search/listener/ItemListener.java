package com.lin.oos.search.listener;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.lin.oos.pojo.PmsProductItem;
import com.lin.oos.service.SearchService;
import io.micrometer.core.instrument.util.JsonUtils;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.TextMessage;

@Component
public class ItemListener {

    @Reference
    private SearchService searchService;

    @JmsListener(destination = "oos-import-singleItem")
    public void onMessage(Message message) throws JMSException {


        TextMessage textMessage = (TextMessage)message;

        searchService.addItem(JSON.parseObject(textMessage.getText(), PmsProductItem.class));





    }

}



