package com.wisdom.framework.core.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * @author hyberbin on 2016/10/30.
 */
@Getter
@Setter
public class ResponseData {
    private String code="success";
    private boolean success=true;
    private String msg="成功";
    private Object data;

    public ResponseData() {
    }

    public ResponseData(Object data) {
        this.data=data;
    }
    public ResponseData(String msg) {
        this.msg=msg;
    }
    public ResponseData(boolean success,String code,String msg,Object data) {
        this.success=success;
        this.code=code;
        this.msg=msg;
        this.data=data;
    }
}
