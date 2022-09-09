package com.agee.common.core.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author qimingjin
 * @date 2022-09-09 16:08
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserExtraEntity implements Serializable {
    private static final long serialVersionUID = 2951562461705556315L;

    /** 登录名称 */
    private String loginName;

    /** 用户名称 */
    private String userName;

    /** 用户类型 */
    private String userType;

    //登录类型 验证码，三方，账户密码
    public  Integer loginType;
}
