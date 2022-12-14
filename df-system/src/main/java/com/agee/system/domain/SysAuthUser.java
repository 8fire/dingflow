package com.agee.system.domain;

import com.agee.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 第三方授权表 sys_auth_user
 * 
 * @author ruoyi
 */
@Data
public class SysAuthUser extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 授权ID */
    private Long authId;

    /** 第三方平台用户唯一ID */
    private String uuid;

    /** 系统用户ID */
    private Long userId;

    /** 登录账号 */
    private String loginName;

    /** 用户昵称 */
    private String userName;

    /** 头像地址 */
    private String avatar;

    /** 用户邮箱 */
    private String email;

    /** 用户来源 */
    private String source;

}
