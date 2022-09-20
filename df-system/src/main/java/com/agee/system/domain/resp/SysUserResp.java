package com.agee.system.domain.resp;

import com.agee.system.domain.SysDept;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * @author qimingjin
 * @date 2022-09-20 15:19
 * @Description:
 */
@Data
public class SysUserResp implements Serializable {
    private static final long serialVersionUID = -7432081343334236723L;
    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "部门ID")
    private Long deptId;

    @ApiModelProperty(value = "登录名称")
    private String loginName;

    @ApiModelProperty(value = "用户名称")
    private String userName;

    @ApiModelProperty(value = "用户类型")
    private String userType;

    @ApiModelProperty(value = "用户邮箱")
    private String email;

    @ApiModelProperty(value = "手机号码")
    private String phonenumber;

    @ApiModelProperty(value = "用户性别")
    private String sex;

    @ApiModelProperty(value = "用户头像")
    private String avatar;

    @ApiModelProperty(value = "帐号状态（'0'-正常 '1'-停用）")
    private String status;

    @ApiModelProperty(value ="最后登陆IP")
    private String loginIp;

    @ApiModelProperty(value = "最后登陆时间")
    private Date loginDate;

    @ApiModelProperty(value = "密码最后更新时间")
    private Date pwdUpdateDate;

    @ApiModelProperty(value = "是否开启高管模式")
    private Boolean isSenior;

    @ApiModelProperty(value = "员工工号")
    private String jobnumber;

    @ApiModelProperty(value = "办公地点")
    private String workPlace;

    @ApiModelProperty(value = "分机号")
    private String tel;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "入职时间")
    private Date hiredDate;

    @ApiModelProperty(value = "组织邮箱,暂时没用")
    private String orgEmail;

    @ApiModelProperty(value = "钉钉用户id")
    private String dingUserId;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "岗位信息")
    private SysDept dept;

    @ApiModelProperty(value = "角色集合")
    private Set<String> roles;

    @ApiModelProperty(value = "权限集合")
    private Set<String> permissions;
}
