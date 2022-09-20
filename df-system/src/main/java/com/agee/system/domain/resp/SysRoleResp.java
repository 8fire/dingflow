package com.agee.system.domain.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author qimingjin
 * @date 2022-09-20 15:24
 * @Description:
 */
@Data
public class SysRoleResp implements Serializable {
    private static final long serialVersionUID = 5671076572882090074L;

    @ApiModelProperty(value = "角色id")
    private Long roleId;

    @ApiModelProperty(value = "父节点Id")
    private Long parentId;

    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "角色权限")
    private String roleKey;

    @ApiModelProperty(value = "角色排序")
    private Integer roleSort;

    @ApiModelProperty(value = "数据范围（1：所有数据权限；2：自定义数据权限；3：本部门数据权限；4：本部门及以下数据权限）")
    private String dataScope;

    @ApiModelProperty(value = "角色状态（0正常 1停用）")
    private String status;

    @ApiModelProperty(value = "角色类型（0-系统角色，1-流程角色）")
    private Integer roleType;

    @ApiModelProperty(value = "是否内置角色（0-内置，1-不）")
    private Integer builtIn;

    @ApiModelProperty(value = "备注")
    private String remark;
}
