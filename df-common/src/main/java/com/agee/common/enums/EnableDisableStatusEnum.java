package com.agee.common.enums;

import java.util.Objects;

/**
 * @author qimingjin
 * @date 2022-09-09 13:53
 * @Description:
 */
public enum EnableDisableStatusEnum {

    ENABLE(0,"启用"),

    DISABLE(1,"禁用"),
            ;

    private Integer value;


    private String msg;

    EnableDisableStatusEnum(Integer value, String msg){
        this.value=value;
        this.msg=msg;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static String getEnableDisableMsg(Integer value){
        String msg="";
        for (EnableDisableStatusEnum statusEnum: EnableDisableStatusEnum.values()){
            if(Objects.equals(value,statusEnum.value)){
                msg=statusEnum.getMsg();
                break;
            }
        }
        return msg;
    }
}
