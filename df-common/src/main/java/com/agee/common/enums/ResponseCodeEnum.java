package com.agee.common.enums;

/**
 * @author qimingjin
 * @date 2022-09-09 13:46
 * @Description: 异常响应枚举
 */
public enum ResponseCodeEnum {

    NO_TOKEN_ERROR(10001,"未提供token"),

    INVALID_TOKEN_ERROR(10002,"无效的token"),

    EXPIRE_TOKEN_ERROR(10003,"token已过期"),

    BE_REPLACED_ERROR(10004,"token已被顶下线"),

    KICK_OUT_ERROR(10005,"token已被踢下线"),

    REPEAT_SUBMIT_ERROR(10006,"请勿频繁操作，请稍后再试"),

    ACCOUNT_NOT_EXIST_ERROR(10007,"账户不存在"),

    ACCOUNT_PASSWORD_ERROR(10008,"用户帐号或密码不正确"),
    ACCOUNT_LOCKED_ERROR(10009,"该帐号已锁定,请联系管理员"),
    ACCOUNT_DISABLED_ERROR(10010,"该账户已被禁用，请联系管理员"),

    ACCOUNT_NOT_AUTH_ERROR(11000,"账户权限不足"),


    SERVER_FAIL(99999, "服务器开小差了，请稍后再试");

    private Integer code;

    private String message;

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    ResponseCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
