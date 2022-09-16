package com.agee.common.core.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @program: df
 * @description
 * @author: 没用的阿吉
 * @create: 2022-09-04 14:37
 **/
@Data
@ApiModel(value = "登录实体")
public class Login implements Serializable {
    private static final long serialVersionUID = -5801589315574617796L;

    @ApiModelProperty(value = "用户名")
    @NotBlank(message = "用户名不能为空")
    private String userName;

    @ApiModelProperty(value = "密码")
    @NotBlank(message = "密码不能为空")
    private String password;

    @ApiModelProperty(value = "验证码Id(获取验证码的时候返回的)")
    private String captchaId;

    @ApiModelProperty(value = "验证码")
    private String code;


}
