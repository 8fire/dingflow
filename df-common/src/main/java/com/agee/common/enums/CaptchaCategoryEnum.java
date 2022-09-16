package com.agee.common.enums;

import cn.hutool.captcha.AbstractCaptcha;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.captcha.ShearCaptcha;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author qimingjin
 * @date 2022-09-15 16:30
 * @Description:
 */
@Getter
@AllArgsConstructor
public enum CaptchaCategoryEnum {

    /**
     * 线段干扰
     */
    LINE(LineCaptcha.class),

    /**
     * 圆圈干扰
     */
    CIRCLE(CircleCaptcha.class),

    /**
     * 扭曲干扰
     */
    SHEAR(ShearCaptcha.class);

    private final Class<? extends AbstractCaptcha> clazz;
}
