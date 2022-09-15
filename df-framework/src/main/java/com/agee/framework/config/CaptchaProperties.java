package com.agee.framework.config;

import com.agee.common.enums.CaptchaCategoryEnum;
import com.agee.common.enums.CaptchaTypeEnum;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author qimingjin
 * @date 2022-09-15 16:13
 * @Description: 验证码
 */
@Data
@Component
@ConfigurationProperties(prefix = "captcha")
public class CaptchaProperties {
    /**
     * 验证码类型
     */
    private CaptchaTypeEnum type;

    /**
     * 验证码类别
     */
    private CaptchaCategoryEnum category;

    /**
     * 数字验证码位数
     */
    private Integer numberLength;

    /**
     * 字符验证码长度
     */
    private Integer charLength;
}
