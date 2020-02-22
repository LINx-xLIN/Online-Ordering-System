package com.lin.oos.crawler.task;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lin.oos.pojo.PmsProductCategory;
import com.lin.oos.service.PmsProductCategoryService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;

@Component
public class PmsProductCategoryPipeline implements Pipeline {

    @Reference
    private PmsProductCategoryService pmsProductCategoryService;


    @Override
    public void process(ResultItems resultItems, Task task) {
        List<PmsProductCategory> pmsProductCategories = resultItems.get("pmsProductCategories");

        if (pmsProductCategories != null) {
            for (PmsProductCategory pmsProductCategory : pmsProductCategories) {
                /*System.out.println(pmsProductCategory.getName());*/
                if (pmsProductCategoryService.findAll(pmsProductCategory).size() == 0) {

                    if(pmsProductCategory.getIsParent()==0){
                        PmsProductCategory pmsProductCategoryParent = pmsProductCategoryService.findByName(pmsProductCategory.getNameParent());
                        pmsProductCategory.setParentId(pmsProductCategoryParent.getId());
                    }



                    pmsProductCategoryService.save(pmsProductCategory);
                }


            }
        }

    }
}
