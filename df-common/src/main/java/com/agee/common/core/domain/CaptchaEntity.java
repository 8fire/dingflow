package com.agee.common.core.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author qimingjin
 * @date 2022-09-16 09:27
 * @Description: 验证码实体
 */
@Data
public class CaptchaEntity implements Serializable {

    @ApiModelProperty(value = "验证码开关")
    private boolean captchaEnabled=true;

    @ApiModelProperty(value = "验证码id")
    private String captchaId;

    @ApiModelProperty(value = "验证码图片Base64")
    private String captchaImage;
}
