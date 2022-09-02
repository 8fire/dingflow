package com.agee.system.domain;

import com.agee.common.core.domain.BaseEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * 用户对象 sys_user
 * 
 * @author snow
 */
@Data
public class SysUser extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 用户ID */
    private Long userId;

    /** 部门ID */
    private Long deptId;

    /** 部门父ID */
    private Long parentId;

    /** 角色ID */
    private Long roleId;

    /** 登录名称 */
    private String loginName;

    /** 用户名称 */
    private String userName;

    /** 用户类型 */
    private String userType;

    /** 用户邮箱 */
    private String email;

    /** 手机号码 */
    private String phonenumber;

    /** 用户性别 */
    private String sex;

    /** 用户头像 */
    private String avatar;

    /** 密码 */
    private String password;

    /** 盐加密 */
    private String salt;

    /** 帐号状态（0正常 1停用） */
    private String status;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    /** 最后登陆IP */
    private String loginIp;

    /** 最后登陆时间 */
    private Date loginDate;

    /** 密码最后更新时间 */
    private Date pwdUpdateDate;


    private SysDept dept;

    private List<SysRole> roles;

    /** 角色组 */
    private Long[] roleIds;

    /** 岗位组 */
    private Long[] postIds;
    /**
     * 	是否开启高管模式：
     * true：开启。开启后，手机号码对所有员工隐藏。普通员工无法对其发DING、发起钉钉免费商务电话。高管之间不受影响。
     * false：不开启。
     */
    private Boolean isSenior;

    /**
     * 	是否号码隐藏：
     * true：隐藏隐藏手机号后，手机号在个人资料页隐藏，但仍可对其发DING、发起钉钉免费商务电话。
     * false：不隐藏
     */
    private Boolean isHide;
    /**
     * 	员工工号，对应显示到OA后台和客户端个人资料的工号栏目。长度为0~64个字符。
     */
    private String jobnumber;

    /**
     * 	办公地点。长度为0~50个字符。
     */
    private String workPlace;

    /**
     * 分机号
     */
    private String tel;
    /**
     * 岗位信息
     */
    private String position;
    /**
     * 入职时间，时间戳
     */

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date hiredDate;
    /**
     * 组织邮箱
     */
    private String orgEmail;

    /**
     * 钉钉用户ID
     */
    private String dingUserId;

    public SysUser() {

    }

    public SysUser(Long userId)
    {
        this.userId = userId;
    }
    public SysUser(Long userId, String userName, String dingUserId)
    {
        this.userId = userId;
        this.userName=userName;
        this.dingUserId=dingUserId;
    }

}
