package com.agee.system.domain.resp;

import com.agee.common.utils.StringUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 路由显示信息
 *
 */
@Data
public class MetaResp implements Serializable {
    private static final long serialVersionUID = 1806768818045834862L;

    @ApiModelProperty(value = "设置该路由在侧边栏和面包屑中展示的名字")
    private String title;

    @ApiModelProperty(value = "设置该路由的图标，对应路径src/assets/icons/svg")
    private String icon;

    @ApiModelProperty(value = "设置为true，则不会被 <keep-alive>缓存")
    private boolean noCache;

    @ApiModelProperty(value = "内链地址（http(s)://开头）")
    private String link;

    public MetaResp() {
    }

    public MetaResp(String title, String icon) {
        this.title = title;
        this.icon = icon;
    }

    public MetaResp(String title, String icon, boolean noCache) {
        this.title = title;
        this.icon = icon;
        this.noCache = noCache;
    }

    public MetaResp(String title, String icon, String link) {
        this.title = title;
        this.icon = icon;
        this.link = link;
    }

    public MetaResp(String title, String icon, boolean noCache, String link) {
        this.title = title;
        this.icon = icon;
        this.noCache = noCache;
        if (StringUtils.ishttp(link)) {
            this.link = link;
        }
    }
}
