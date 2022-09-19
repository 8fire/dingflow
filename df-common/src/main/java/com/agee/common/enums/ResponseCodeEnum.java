package com.agee.common.enums;

/**
 * @author qimingjin
 * @date 2022-09-09 13:46
 * @Description: 异常响应枚举
 */
public enum ResponseCodeEnum {

    // 错误码为整行类型，共 5 位，分成两个部分：错误产生来源+四位数字编号。1-标识鉴权相关 2-基础服务 3-钉钉服务 4-流程服务 5-表单服务
    //第二个数字表示服务下的某个模块   第3-5位表示自增错误码，我们遵循先来先占坑的原则，比如20001 表示用户登录名称已存在，现在有个用户昵称的异常，则自动占用20002

    NO_LOGIN_ERROR(10000,"当前会话未登录"),

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

    ACCOUNT_OLD_PASSWORD_ERROR(10011,"老密码不正确"),

    ACCOUNT_OLD_NEW_SOME_PASSWORD_ERROR(10012,"新密码不能与老密码相同"),

    ACCOUNT_CAPTCHA_EXPIRE_ERROR(10013,"验证码已过期"),

    ACCOUNT_CAPTCHA_ERROR(10014,"验证码不正确"),

    ACCOUNT_NOT_AUTH_ERROR(11000,"没有访问权限，请联系管理员授权"),




    //用户相关
    LOGIN_USER_EXIST_ERROR(20001,"登录账号已存在"),

    USER_NAME_EXIST_ERROR(20002,"用户昵称已存在"),

    USER_EMAIL_EXIST_ERROR(20003,"用户邮箱已存在"),

    USER_PHONE_EXIST_ERROR(20004,"用户手机号已存在"),


    //角色相关
    ROLE_NAME_EXIST_ERROR(21001,"角色名称已存在"),

    ROLE_KEY_EXIST_ERROR(21002,"角色key已存在"),

    ROLE_BUILT_IN_ERROR(21003,"内置角色不允许修改"),

    ROLE_ALLOT_ERROR(21004,"角色已被分配，不能删除"),








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
