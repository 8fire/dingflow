package com.agee.common.config;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author qimingjin
 * @date 2022-09-15 14:11
 * @Description:
 */
@Data
@Component
@ConfigurationProperties(prefix = "df")
public class DingFlowConfig {
    /**
     * 项目名称
     */
    private String name;

    /**
     * 版本
     */
    private String version;

    /**
     * 版权年份
     */
    private String copyrightYear;

    /**
     * 实例演示开关
     */
    private boolean demoEnabled;

    /**
     * 缓存懒加载
     */
    private boolean cacheLazy;

    /**
     * 获取地址开关
     */
    @Getter
    private static boolean addressEnabled;

    public void setAddressEnabled(boolean addressEnabled) {
        DingFlowConfig.addressEnabled = addressEnabled;
    }
}
