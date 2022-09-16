package com.agee.admin.web.common;

import cn.hutool.captcha.AbstractCaptcha;
import cn.hutool.captcha.generator.CodeGenerator;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.agee.common.core.constant.CacheConstants;
import com.agee.common.core.constant.Constants;
import com.agee.common.core.domain.CaptchaEntity;
import com.agee.common.core.domain.R;
import com.agee.common.enums.CaptchaTypeEnum;
import com.agee.common.utils.RedisUtils;
import com.agee.framework.config.CaptchaProperties;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

/**
 * @author qimingjin
 * @date 2022-09-16 08:37
 * @Description: 验证码
 */
@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@ApiSort(2)
@Api(value = "验证码服务")
public class CaptchaController {

    private final CaptchaProperties captchaProperties;

    @ApiOperation(value = "生成验证码",notes = "该接口用于生成验证码")
    @GetMapping("/captchaImage")
    public R<CaptchaEntity> getCode() {
        CaptchaEntity captchaEntity=new CaptchaEntity();
        boolean captchaEnabled = captchaProperties.isEnabled();
        captchaEntity.setCaptchaEnabled(captchaEnabled);
        if (!captchaEnabled) {
            return R.ok(captchaEntity);
        }
        // 保存验证码信息
        String uuid = IdUtil.simpleUUID();
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + uuid;
        // 生成验证码
        CaptchaTypeEnum captchaType = captchaProperties.getType();
        boolean isMath = CaptchaTypeEnum.MATH == captchaType;
        Integer length = isMath ? captchaProperties.getNumberLength() : captchaProperties.getCharLength();
        CodeGenerator codeGenerator = ReflectUtil.newInstance(captchaType.getClazz(), length);
        AbstractCaptcha captcha = SpringUtil.getBean(captchaProperties.getCategory().getClazz());
        captcha.setGenerator(codeGenerator);
        captcha.createCode();
        String code = isMath ? getCodeResult(captcha.getCode()) : captcha.getCode();
        RedisUtils.setCacheObject(verifyKey, code, Duration.ofMinutes(Constants.CAPTCHA_EXPIRATION));
        captchaEntity.setCaptchaId(uuid);
        captchaEntity.setCaptchaImage(captcha.getImageBase64Data());
        return R.ok(captchaEntity);
    }

    private String getCodeResult(String capStr) {
        int numberLength = captchaProperties.getNumberLength();
        int a = Convert.toInt(StrUtil.sub(capStr, 0, numberLength).trim());
        char operator = capStr.charAt(numberLength);
        int b = Convert.toInt(StrUtil.sub(capStr, numberLength + 1, numberLength + 1 + numberLength).trim());
        switch (operator) {
            case '*':
                return Convert.toStr(a * b);
            case '+':
                return Convert.toStr(a + b);
            case '-':
                return Convert.toStr(a - b);
            default:
                return StrUtil.EMPTY;
        }
    }
}
