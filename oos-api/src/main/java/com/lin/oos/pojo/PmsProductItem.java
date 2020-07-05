package com.lin.oos.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.io.Serializable;
import java.util.Date;


public class PmsProductItem implements Serializable {



    @Field("id")/*使用这个注释，里面的名字是根据你在solr数据库中配置的来决定*/
    private Integer id;
    @Field("title")
    private String title;
    @Field("ingredients")
    private String ingredients;
    @Field("price")
    private Double price;
    @Field("num")
    private Long num;
    @Field("image")
    private String image;
    @Field("cid")
    private Integer cid;
    @Field("status")
    private Integer status;
    @Field("create")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date create;
    @Field("categoryName")
    private String categoryName;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreate() {
        return create;
    }

    public void setCreate(Date create) {
        this.create = create;
    }
}