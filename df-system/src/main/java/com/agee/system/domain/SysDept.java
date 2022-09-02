package com.agee.system.domain;

import com.agee.common.core.domain.BaseEntity;
import lombok.Data;

import java.util.List;

/**
 * 部门表 sys_dept
 * 
 * @author snow
 */
@Data
public class SysDept extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 部门ID */
    private Long deptId;

    /**
     * 钉钉部门id
     */
    private Long newDeptId;

    /** 父部门ID */
    private Long parentId;

    /** 祖级列表 */
    private String ancestors;

    /** 部门名称 */
    private String deptName;

    /** 显示顺序 */
    private String orderNum;

    /** 负责人 */
    private List<String> leader;

    /** 联系电话 */
    private String phone;

    /** 邮箱 */
    private String email;

    /** 部门状态:0正常,1停用 */
    private String status;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    /** 父部门名称 */
    private String parentName;


}
