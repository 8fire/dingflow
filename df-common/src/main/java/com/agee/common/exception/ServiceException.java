package com.agee.common.exception;

import com.agee.common.enums.ResponseCodeEnum;

/**
 * @author qimingjin
 * @date 2022-09-09 13:48
 * @Description:
 */
public class ServiceException extends RuntimeException{
    private static final long serialVersionUID = -2676031306919674764L;
    /**
     * 异常编码
     */
    private Integer code;


    public ServiceException() {
        super();
    }


    public ServiceException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public ServiceException(ResponseCodeEnum responseCodeEnum) {
        super(responseCodeEnum.getMessage());
        this.code = responseCodeEnum.getCode();
    }

    public Integer getCode() {
        return code;
    }
}
