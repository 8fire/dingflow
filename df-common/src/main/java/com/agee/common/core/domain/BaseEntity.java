package com.agee.common.core.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * Entity基类
 *
 */
@Data
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 搜索值 */
    @TableField(exist = false)
    private String searchValue;

    /** 创建者 */
    private String createBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    /** 更新者 */
    private String updateBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /** 删除标识 */
    @TableLogic
    private Integer delFlag;

    /** 请求参数 */
    @TableField(exist = false)
    private Map<String, Object> params;

    @TableField(exist = false)
    private String orderBy;
    /**
     * 是否同步到钉钉
     */
    @TableField(exist = false)
    private Boolean isSyncDingTalk=true;


    public static final  String CREATE_TIME=" createTime";

    public static final  String UPDATE_TIME=" updateTime";

    public static final  String CREATE_BY=" createBy";

    public static final  String UPDATE_BY="updateBy";


}
