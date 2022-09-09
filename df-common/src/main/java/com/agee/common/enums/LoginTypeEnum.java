package com.agee.common.enums;
import java.util.Objects;

public enum LoginTypeEnum {

    PASSWORD(1,"密码登录"),

    CODE(2,"验证码登录"),

    WX_APP(3,"微信快捷登录");



    private Integer code;

    private String msg;

    LoginTypeEnum(Integer code, String msg){
        this.code=code;
        this.msg=msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static String getLoginTypeMsg(Integer code){
        String msg="";
        for (LoginTypeEnum loginTypeEnum: LoginTypeEnum.values()){
            if(Objects.equals(code,loginTypeEnum.code)){
                msg=loginTypeEnum.getMsg();
                break;
            }
        }
        return msg;
    }
}
