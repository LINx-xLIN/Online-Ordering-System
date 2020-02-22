package com.lin.oos.crawler.task;

import com.lin.oos.pojo.PmsProductCategory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.util.ArrayList;
import java.util.List;

public class PmsProductCategoryProcessor implements PageProcessor {


    private Site site = Site.me()
            .setCharset("UTF-8")//设置编码
            .setTimeOut(10*1000)//设置超时时间
            .setRetrySleepTime(3000)//设置重试的间隔时间
            .setRetryTimes(3);//设置重试的次数



    @Override
    public void process(Page page) {
        /*System.out.println(page);*/

        List<Selectable> nodes = page.getHtml().css("div.w.clear div.category_box.mt20 div.category_sub.clear").nodes();


        List<PmsProductCategory> pmsProductCategories = new ArrayList<PmsProductCategory>();


        /*System.out.println(nodes.get(0).css("ul li").nodes().size());*/


        for (Selectable node : nodes) {

            PmsProductCategory pmsProductCategory = new PmsProductCategory();
            pmsProductCategory.setName(node.css("h3", "text").toString());
            pmsProductCategory.setIsParent(1);
            pmsProductCategory.setStatus(1);
            pmsProductCategories.add(pmsProductCategory);



            List<Selectable> node1s = node.css("ul li").nodes();

            for (Selectable node1 : node1s) {
                /*System.out.println(node1.css("a", "text").toString());*/

                PmsProductCategory pmsProductCategory1 = new PmsProductCategory();
                pmsProductCategory1.setName(node1.css("a", "text").toString());
                pmsProductCategory1.setNameParent(node.css("h3", "text").toString());
                pmsProductCategory1.setIsParent(0);
                pmsProductCategory1.setStatus(1);
                pmsProductCategories.add(pmsProductCategory1);

            }



        }

        page.putField("pmsProductCategories",pmsProductCategories);
    }

    @Override
    public Site getSite() {
        return this.site;
    }
}
