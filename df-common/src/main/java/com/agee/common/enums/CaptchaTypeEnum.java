package com.agee.common.enums;

import cn.hutool.captcha.generator.CodeGenerator;
import cn.hutool.captcha.generator.RandomGenerator;
import com.agee.common.captcha.UnsignedMathGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author qimingjin
 * @date 2022-09-15 16:14
 * @Description: 验证码类型
 */
@Getter
@AllArgsConstructor
public enum CaptchaTypeEnum {
    /**
     * 数字
     */
    MATH(UnsignedMathGenerator.class),

    /**
     * 字符
     */
    CHAR(RandomGenerator.class);

    private final Class<? extends CodeGenerator> clazz;
}
