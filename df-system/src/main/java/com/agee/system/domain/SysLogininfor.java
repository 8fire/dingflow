package com.agee.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * 系统访问记录表 sys_logininfor
 * 
 * @author snow
 */
@Data
public class SysLogininfor implements Serializable {
    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(type = IdType.AUTO)
    private Long infoId;

    /** 用户账号 */
    private String loginName;

    /** 登录状态 0成功 1失败 */
    private String status;

    /** 登录IP地址 */
    private String ipaddr;

    /** 登录地点 */
    private String loginLocation;

    /** 浏览器类型 */
    private String browser;

    /** 操作系统 */
    private String os;

    /** 提示消息 */
    private String msg;

    /** 访问时间 */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date loginTime;

    /** 请求参数 */
    @TableField(exist = false)
    private Map<String, Object> params;
}