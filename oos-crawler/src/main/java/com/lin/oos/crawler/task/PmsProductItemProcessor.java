package com.lin.oos.crawler.task;

import com.lin.oos.pojo.PmsProductItem;
import org.jsoup.Jsoup;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.util.ArrayList;
import java.util.List;

public class PmsProductItemProcessor implements PageProcessor {

    private static int countTitlePage = 0;

    private Site site = Site.me()
            .setCharset("UTF-8")//设置编码
            .setTimeOut(10*1000)//设置超时时间
            .setRetrySleepTime(3000)//设置重试的间隔时间
            .setRetryTimes(3);//设置重试的次数



    @Override
    public void process(Page page) {
        /*System.out.println(page);*/

        List<Selectable> nodes = page.getHtml().css("div.w.clear div.category_box.mt20 div.category_sub.clear").nodes();




        /*System.out.println(nodes.get(0).css("ul li").nodes().size());*/

        if(nodes.size()==0){
            List<Selectable> itemNodes = page.getHtml().css("div#J_list ul li").nodes();


            List<PmsProductItem> pmsProductItems = new ArrayList<>();

            for (Selectable itemNode : itemNodes) {
                PmsProductItem pmsProductItem = new PmsProductItem();
                pmsProductItem.setTitle(itemNode.css("div.detail h2 a","text").toString());
                pmsProductItem.setImage(Jsoup.parse(itemNode.css("div.pic a img").toString()).select("img").attr("data-src"));
                pmsProductItem.setCategoryName(page.getHtml().css("div.ui_title_wrap.clear h1.on a","text").toString());
                pmsProductItem.setIngredients(itemNode.css("div.detail p.subcontent","text").toString().split("原料：")[1]);
                pmsProductItem.setNum((long)(Math.random()*100)+1);


                pmsProductItem.setPrice(Double.valueOf(String.format("%.2f",((Math.random()*10)+1))));

                pmsProductItem.setStatus(1);
                pmsProductItems.add(pmsProductItem);

            }
            page.putField("pmsProductItems",pmsProductItems);

            String nextUrl = Jsoup.parse(page.getHtml().toString()).select("div.ui-page.mt10 div.ui-page-inner a.now_page").next().attr("href");


            if (nextUrl != null && countTitlePage<1) {
                countTitlePage++;
                page.addTargetRequest(nextUrl);
            }


        }else {

            for (Selectable node : nodes) {
                List<Selectable> node1s = node.css("ul li").nodes();

                for (Selectable node1 : node1s) {

                    page.addTargetRequest(node1.links().toString());


                }




            }



        }



    }

    @Override
    public Site getSite() {
        return this.site;
    }
}
