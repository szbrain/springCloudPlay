package com.licslan.result;


import lombok.Data;

import java.io.Serializable;

/**
 * 响应数据
 */
@Data
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编码：0表示成功，其他值表示失败
     */
    private int code = 0;

    /**
     * 消息内容
     */
    private String msg = "success";

    /**
     * 响应数据
     */
    private T data;

    public Result<T> ok(T data) {
        this.setData(data);
        return this;
    }

    public boolean success() {
        return code == 0;
    }


    public Result<T> error(int code, String msg) {
        this.code = code;
        this.msg = msg;
        return this;
    }

}
