package com.agee.common.core.constant;

/**
 * 通用常量信息
 *
 */
public class Constants {
    /**
     * UTF-8 字符集
     */
    public static final String UTF8 = "UTF-8";

    /**
     * GBK 字符集
     */
    public static final String GBK = "GBK";

    /**
     * token 名称
     */
    public static final String DF_USER_TOKEN = "X-DF-USER-TOKEN";

    /**
     * http请求
     */
    public static final String HTTP = "http://";

    /**
     * https请求
     */
    public static final String HTTPS = "https://";

    /**
     * 成功标记
     */
    public static final Integer SUCCESS = 200;

    /**
     * 失败标记
     */
    public static final Integer FAIL = 500;

    /**
     * 登录成功状态
     */
    public static final String LOGIN_SUCCESS_STATUS = "0";

    /**
     * 登录失败状态
     */
    public static final String LOGIN_FAIL_STATUS = "1";

    /**
     * 登录成功
     */
    public static final String LOGIN_SUCCESS = "Success";

    /**
     * 注销
     */
    public static final String LOGOUT = "Logout";

    /**
     * 注册
     */
    public static final String REGISTER = "Register";

    /**
     * 登录失败
     */
    public static final String LOGIN_FAIL = "Error";


    /**
     * 排序列
     */
    public static final String ORDER_BY_COLUMN = "orderByColumn";

    /**
     * 排序的方向 "desc" 或者 "asc".
     */
    public static final String IS_ASC = "isAsc";


    //登录类型 验证码，三方，账户密码
    public final static String LOGIN_TYPE = "login_type";

    //登录用户
    public final static String LOGIN_USER = "login_user";

    /**
     * 管理员ID
     */
    public final static Long ADMIN_ID = 1L;

    /**
     * 验证码有效期（分钟）
     */
    public final static Integer CAPTCHA_EXPIRATION = 2;

    /**
     * 系统内置状态
     */
    public static final Integer BUILT_IN_STATUS = 0;

    /**
     * 角色类型
     */
    public static final Integer SYSTEM_ROLE_TYPE = 1;
    public static final Integer FLOW_ROLE_TYPE = 2;
}
