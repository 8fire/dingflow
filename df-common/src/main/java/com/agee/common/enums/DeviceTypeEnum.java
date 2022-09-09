package com.agee.common.enums;

import java.util.Objects;

/**
 * @author qimingjin
 * @date 2022-06-08 10:42
 * @Description:
 */
public enum DeviceTypeEnum {
    PC("PC","PC端"),

    APP("APP","APP端"),

    APPLET("Applet","小程序"),

;



    private String code;

    private String msg;

    DeviceTypeEnum(String code, String msg){
        this.code=code;
        this.msg=msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static String getLoginTypeMsg(String code){
        String msg="";
        for (DeviceTypeEnum deviceTypeEnum: DeviceTypeEnum.values()){
            if(Objects.equals(code,deviceTypeEnum.code)){
                msg=deviceTypeEnum.getMsg();
                break;
            }
        }
        return msg;
    }
}
