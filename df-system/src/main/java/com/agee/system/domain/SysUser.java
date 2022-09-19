package com.agee.system.domain;

import com.agee.common.core.constant.Constants;
import com.agee.common.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * 用户对象 sys_user
 * 
 * @author snow
 */
@Data
@TableName("sys_user")
@AllArgsConstructor
@NoArgsConstructor
public class SysUser extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
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

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "盐值", hidden = true)
    private String salt;

    @ApiModelProperty(value = "帐号状态（'0'-正常 '1'-停用）")
    private String status;

    @ApiModelProperty(value ="最后登陆IP", hidden = true)
    private String loginIp;

    @ApiModelProperty(value = "最后登陆时间")
    private Date loginDate;

    @ApiModelProperty(value = "密码最后更新时间")
    private Date pwdUpdateDate;

    /**
     * 是否开启高管模式：
     * true：开启。开启后，手机号码对所有员工隐藏。普通员工无法对其发DING、发起钉钉免费商务电话。高管之间不受影响。
     * false：不开启。
     */
    @ApiModelProperty(value = "是否开启高管模式")
    private Boolean isSenior;

    /**
     * 员工工号，对应显示到OA后台和客户端个人资料的工号栏目。长度为0~64个字符。
     */
    @ApiModelProperty(value = "员工工号")
    private String jobnumber;

    /**
     * 办公地点。长度为0~50个字符。
     */
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

    @TableField(exist = false)
    private String deptName;

    @TableField(exist = false)
    private SysDept dept;

    @TableField(exist = false)
    private List<SysRole> roles;

    /**
     * 角色组
     */
    @TableField(exist = false)
    private Long[] roleIds;

    /**
     * 岗位组
     */
    @TableField(exist = false)
    private Long[] postIds;

    public boolean isAdmin() {
        return Constants.ADMIN_ID.equals(this.userId);
    }

    public SysUser(Long userId){
        this.userId=userId;
    }

}
