package com.agee.system.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户和角色关联 sys_user_role
 * 
 * @author snow
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUserRole implements Serializable {
    private static final long serialVersionUID = -4017188187512516422L;
    /** 用户ID */
    private Long userId;
    
    /** 角色ID */
    private Long roleId;

}
