package com.lin.oos.vo;

import java.io.Serializable;

public class OosResult implements Serializable {

    private Integer status;
    private String msg;
    private Object data;


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static OosResult build(Integer status,String msg,Object data){
        return new OosResult(status,msg,data);
    }
    public static OosResult build(Integer status,String msg){
        return new OosResult(status,msg,null);
    }

    public static OosResult ok(Object data){
        return new OosResult(data);
    }


    public static OosResult ok() {
        return new OosResult(null);
    }

    public OosResult(Object data) {
        this.status = 200;
        this.msg = "OK";
        this.data = data;
    }

    public OosResult(int status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }




    public OosResult() {
    }
}
