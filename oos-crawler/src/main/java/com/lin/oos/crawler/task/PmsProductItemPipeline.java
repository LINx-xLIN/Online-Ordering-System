package com.lin.oos.crawler.task;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lin.oos.crawler.util.DownloadImage;
import com.lin.oos.pojo.PmsProductCategory;
import com.lin.oos.pojo.PmsProductItem;
import com.lin.oos.service.PmsProductCategoryService;
import com.lin.oos.service.PmsProductItemService;
import com.lin.oos.service.UploadService;
import com.lin.oos.vo.UploadResult;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;

@Component
public class PmsProductItemPipeline implements Pipeline {

    @Reference
    private PmsProductItemService pmsProductItemService;

    @Reference
    private PmsProductCategoryService pmsProductCategoryService;


    @Reference
    private UploadService uploadService;

    @Override
    public void process(ResultItems resultItems, Task task) {
        List<PmsProductItem> pmsProductItems = resultItems.get("pmsProductItems");


        if (pmsProductItems != null) {
            for (PmsProductItem pmsProductItem : pmsProductItems) {

                PmsProductItem productItem = pmsProductItemService.findByTitle(pmsProductItem.getTitle());

                if (productItem == null) {

                    PmsProductCategory pmsProductCategory = pmsProductCategoryService.findByName(pmsProductItem.getCategoryName());

                    pmsProductItem.setCid(pmsProductCategory.getId());


                    byte[] bytes = DownloadImage.downloadImg(pmsProductItem.getImage());

                    UploadResult upload = uploadService.upload(".jpg", bytes);
                    pmsProductItem.setImage(upload.getUrl());


                    pmsProductItemService.save(pmsProductItem);
                }



            }
        }





    }
}
