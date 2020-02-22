package com.lin.oos.pojo;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;

public class PmsProductCategory implements Serializable {
    /**
     * id
     */
    private Integer id;

    /**
     * parentId
     */
    private Integer parentId;

    /**
     * name
     */
    private String name;

    /**
     * status：0->失效，1->有效
     */
    private Integer status;

    /**
     * isParent：0->没有，1->有
     */
    private Integer isParent;

    /**
     * created
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date created;

    /**
     * name_parent
     */
    private String nameParent;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsParent() {
        return isParent;
    }

    public void setIsParent(Integer isParent) {
        this.isParent = isParent;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getNameParent() {
        return nameParent;
    }

    public void setNameParent(String nameParent) {
        this.nameParent = nameParent;
    }
}