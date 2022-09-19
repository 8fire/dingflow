package com.agee.system.domain.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @author qimingjin
 * @date 2022-09-19 15:24
 * @Description:
 */
@Data
public class SysUserPageQueryReq implements Serializable {
    private static final long serialVersionUID = 4490403562689927420L;

    @ApiModelProperty(value = "部门ID")
    private Long deptId;

    @ApiModelProperty(value = "用户名称")
    private String userName;

    @ApiModelProperty(value = "手机号码")
    private String phonenumber;

    @ApiModelProperty(value = "帐号状态（'0'-正常 '1'-停用）")
    private String status;

    @ApiModelProperty(value = "请求参数(params[beginTime] params[endTime])")
    private Map<String, Object> params;
}
