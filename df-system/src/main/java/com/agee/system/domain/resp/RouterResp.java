package com.agee.system.domain.resp;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 路由配置信息
 *
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
public class RouterResp implements Serializable {

    private static final long serialVersionUID = -4411848160647672537L;
    @ApiModelProperty(value = "路由名字")
    private String name;

    @ApiModelProperty(value = "路由地址")
    private String path;

    @ApiModelProperty(value = "是否隐藏路由，当设置 true 的时候该路由不会再侧边栏出现")
    private boolean hidden;

    @ApiModelProperty(value = "重定向地址，当设置 noRedirect 的时候该路由在面包屑导航中不可被点击")
    private String redirect;

    @ApiModelProperty(value = "组件地址")
    private String component;

    @ApiModelProperty(value = "路由参数：如 {\"id\": 1, \"name\": \"ry\"}")
    private String query;

    @ApiModelProperty(value = "当你一个路由下面的 children 声明的路由大于1个时，自动会变成嵌套的模式--如组件页面")
    private Boolean alwaysShow;

    @ApiModelProperty(value = "其他元素")
    private MetaResp meta;

    @ApiModelProperty(value = "子路由")
    private List<RouterResp> children;

}
