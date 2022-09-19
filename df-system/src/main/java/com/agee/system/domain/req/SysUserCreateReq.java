package com.agee.system.domain.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author qimingjin
 * @date 2022-09-19 14:25
 * @Description:
 */
@Data
@ApiModel(value = "用户新增实体")
public class SysUserCreateReq implements Serializable {
    private static final long serialVersionUID = 5263600422697315664L;

    @ApiModelProperty(value = "部门ID")
    @NotNull(message = "部门ID不能为空")
    private Long deptId;

    @ApiModelProperty(value = "登录名称")
    @NotBlank(message = "登录名称不能为空")
    private String loginName;

    @ApiModelProperty(value = "用户名称")
    @NotBlank(message = "用户名称不能为空")
    private String userName;

    @ApiModelProperty(value = "用户类型(00-系统用户，01-官网用户)")
    private String userType="00";

    @ApiModelProperty(value = "用户邮箱")
    @NotBlank(message = "用户邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;

    @ApiModelProperty(value = "手机号码")
    @NotBlank(message = "手机号码不能为空")
    private String phonenumber;

    @ApiModelProperty(value = "用户性别")
    @NotBlank(message = "用户性别不能为空")
    private String sex;

    @ApiModelProperty(value = "用户头像")
    private String avatar;

    @ApiModelProperty(value = "密码")
    @NotBlank(message = "密码不能为空")
    private String password;

    @ApiModelProperty(value = "帐号状态（'0'-正常 '1'-停用）")
    private String status="0";

    @ApiModelProperty(value = "是否开启高管模式")
    private Boolean isSenior;

    /**
     * 	员工工号，对应显示到OA后台和客户端个人资料的工号栏目。长度为0~64个字符。
     */
    @ApiModelProperty(value = "员工工号")
    private String jobnumber;

    /**
     * 	办公地点。长度为0~50个字符。
     */
    @ApiModelProperty(value = "办公地点")
    private String workPlace;

    /**
     * 分机号
     */
    @ApiModelProperty(value = "分机号")
    private String tel;


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "入职时间")
    private Date hiredDate;

    @ApiModelProperty(value = "组织邮箱,暂时没用")
    private String orgEmail;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "角色组")
    private Long[] roleIds;

    @ApiModelProperty(value = "岗位组")
    private Long[] postIds;
}
