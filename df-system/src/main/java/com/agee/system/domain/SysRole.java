package com.agee.system.domain;

import com.agee.common.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 角色表 sys_role
 * 
 * @author snow
 */
@Data
public class SysRole extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 角色ID */
    @TableId(type = IdType.AUTO)
    private Long roleId;

    /**
     * 父节点
     */
    private Long parentId;

    /** 角色名称 */

    private String roleName;

    /** 角色权限 */

    private String roleKey;

    /** 角色排序 */

    private Integer roleSort;

    /** 数据范围（1：所有数据权限；2：自定义数据权限；3：本部门数据权限；4：本部门及以下数据权限） */
    private String dataScope;

    /** 角色状态（0正常 1停用） */
    private String status;

    /**
     * 角色类型（0-系统角色，1-流程角色）
     */
    private Integer roleType;

    /**
     * 是否内置角色（0-内置，1-不）
     */
    private Integer builtIn;

    /** 备注 */
    private String remark;

    /** 用户是否存在此角色标识 默认不存在 */
    @TableField(exist = false)
    private boolean flag = false;

    /** 菜单组 */
    @TableField(exist = false)
    private Long[] menuIds;

    /** 部门组（数据权限） */
    @TableField(exist = false)
    private Long[] deptIds;

    /**
     * 角色菜单权限
     */
    @TableField(exist = false)
    private Set<String> permissions;

    /** 子菜单 */
    @TableField(exist = false)
    private List<SysRole> children = new ArrayList<>();

}
