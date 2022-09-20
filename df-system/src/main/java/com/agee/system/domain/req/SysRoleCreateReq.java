package com.agee.system.domain.req;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author qimingjin
 * @date 2022-09-19 16:44
 * @Description: 新增角色实体
 */
@Data
@ApiModel(value = "新增角色实体")
public class SysRoleCreateReq implements Serializable {
    private static final long serialVersionUID = 5332333918142520313L;

    @ApiModelProperty(value = "角色名称")
    @NotBlank(message = "角色名称不能为空")
    private String roleName;

    @ApiModelProperty(value = "角色权限key")
    @NotBlank(message = "角色名称不能为空")
    private String roleKey;

    @ApiModelProperty(value = "角色排序")
    private Integer roleSort;

    @ApiModelProperty(value = "数据范围（1：所有数据权限；2：自定义数据权限；3：本部门数据权限；4：本部门及以下数据权限）")
    private String dataScope;

    @ApiModelProperty(value = "（'0'正常 '1'停用）")
    private String status;

    @ApiModelProperty(value = "是否内置角色（0-内置，1-不）")
    private Integer builtIn;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "菜单权限组")
    private Long[] menuIds;
}
