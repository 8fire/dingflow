package com.agee.system.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 角色和菜单关联 sys_role_menu
 * 
 * @author snow
 */
@Data
public class SysRoleMenu implements Serializable {
    private static final long serialVersionUID = -7116437051136647863L;
    /** 角色ID */
    private Long roleId;
    
    /** 菜单ID */
    private Long menuId;

}
