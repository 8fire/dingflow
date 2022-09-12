package com.agee.common.exception;

import com.agee.common.enums.ResponseCodeEnum;

/**
 * @program: df
 * @description demo演示异常
 * @author: 没用的阿吉
 * @create: 2022-09-12 11:01
 **/
public class DemoModeException extends RuntimeException{

    private static final long serialVersionUID = -8310804975084869775L;
    /**
     * 异常编码
     */
    private Integer code;


    public DemoModeException() {
        super();
    }


    public DemoModeException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public DemoModeException(ResponseCodeEnum responseCodeEnum) {
        super(responseCodeEnum.getMessage());
        this.code = responseCodeEnum.getCode();
    }

    public Integer getCode() {
        return code;
    }
}
