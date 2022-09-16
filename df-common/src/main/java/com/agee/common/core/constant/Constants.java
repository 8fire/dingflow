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
    public static final String DF_USER_TOKEN = "DF-USER-TOKEN";

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
     * 当前记录起始索引
     */
    public static final String PAGE_NUM = "pageNum";

    /**
     * 每页显示记录数
     */
    public static final String PAGE_SIZE = "pageSize";

    /**
     * 排序列
     */
    public static final String ORDER_BY_COLUMN = "orderByColumn";

    /**
     * 排序的方向 "desc" 或者 "asc".
     */
    public static final String IS_ASC = "isAsc";


    public final static String LOGIN_USER_NAME = "login_user_name";


    public final static String LOGIN_NICK_NAME = "login_nick_name";

    public final static String LOGIN_USER_TYPE = "user_type";

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
}
