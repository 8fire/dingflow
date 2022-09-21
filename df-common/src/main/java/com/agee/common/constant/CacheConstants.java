package com.agee.common.constant;

/**
 * @author qimingjin
 * @date 2022-09-16 08:50
 * @Description: 缓存key
 */
public class CacheConstants {

    /**
     * 验证码 redis key
     */
    public static final String CAPTCHA_CODE_KEY = "DF:CAPTCHA_CODES:";

    /**
     * 在线用户 redis key
     */
    public static final String ONLINE_TOKEN_KEY = "DF:ONLINE:TOKENS:";

    /**
     * 系统token key
     */
    public static final String DF_USER_TOKEN_KEY = "X-DF-USER-TOKEN:login:token:";
}
