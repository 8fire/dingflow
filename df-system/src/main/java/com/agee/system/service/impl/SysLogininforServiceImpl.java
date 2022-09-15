package com.agee.system.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.agee.common.core.constant.Constants;
import com.agee.common.utils.AddressUtils;
import com.agee.system.domain.SysLogininfor;
import com.agee.system.mapper.SysLogininforMapper;
import com.agee.system.service.SysLogininforService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author qimingjin
 * @date 2022-09-15 13:52
 * @Description:
 */
@Service
@Slf4j
public class SysLogininforServiceImpl extends ServiceImpl<SysLogininforMapper, SysLogininfor>  implements SysLogininforService {
    /**
     * 记录登录信息
     *
     * @param username 用户名
     * @param status   状态
     * @param message  消息
     * @param args     列表
     */
    @Async
    @Override
    public void recordLogininfor(final String username, final String status, final String message,
                                 HttpServletRequest request, final Object... args) {
        final UserAgent userAgent = UserAgentUtil.parse(request.getHeader("User-Agent"));
        final String ip = ServletUtil.getClientIP(request);

        String address = AddressUtils.getRealAddressByIP(ip);
        StringBuilder s = new StringBuilder();
        s.append(getBlock(ip));
        s.append(address);
        s.append(getBlock(username));
        s.append(getBlock(status));
        s.append(getBlock(message));
        // 打印信息到日志
        log.info(s.toString(), args);
        // 获取客户端操作系统
        String os = userAgent.getOs().getName();
        // 获取客户端浏览器
        String browser = userAgent.getBrowser().getName();
        // 封装对象
        SysLogininfor logininfor = new SysLogininfor();
        logininfor.setLoginName(username);
        logininfor.setIpaddr(ip);
        logininfor.setLoginLocation(address);
        logininfor.setBrowser(browser);
        logininfor.setOs(os);
        logininfor.setMsg(message);
        // 日志状态
        if (StrUtil.equalsAny(status, Constants.LOGIN_SUCCESS, Constants.LOGOUT, Constants.REGISTER)) {
            logininfor.setStatus("0");
        } else if (Constants.LOGIN_FAIL.equals(status)) {
            logininfor.setStatus("1");
        }
        // 插入数据
        insertLogininfor(logininfor);
    }

    private String getBlock(Object msg) {
        if (msg == null) {
            msg = "";
        }
        return "[" + msg.toString() + "]";
    }


    /**
     * 新增系统登录日志
     *
     * @param logininfor 访问日志对象
     */
    @Override
    public void insertLogininfor(SysLogininfor logininfor) {
        logininfor.setLoginTime(new Date());
        baseMapper.insert(logininfor);
    }

    /**
     * 查询系统登录日志集合
     *
     * @param logininfor 访问日志对象
     * @return 登录记录集合
     */
    @Override
    public List<SysLogininfor> selectLogininforList(SysLogininfor logininfor) {
        Map<String, Object> params = logininfor.getParams();
        return baseMapper.selectList(new LambdaQueryWrapper<SysLogininfor>()
                .like(StrUtil.isNotBlank(logininfor.getIpaddr()), SysLogininfor::getIpaddr, logininfor.getIpaddr())
                .eq(StrUtil.isNotBlank(logininfor.getStatus()), SysLogininfor::getStatus, logininfor.getStatus())
                .like(StrUtil.isNotBlank(logininfor.getLoginName()), SysLogininfor::getLoginName, logininfor.getLoginName())
                .between(params.get("beginTime") != null && params.get("endTime") != null,
                        SysLogininfor::getLoginTime, params.get("beginTime"), params.get("endTime"))
                .orderByDesc(SysLogininfor::getInfoId));
    }

    /**
     * 批量删除系统登录日志
     *
     * @param infoIds 需要删除的登录日志ID
     * @return 结果
     */
    @Override
    public int deleteLogininforByIds(Long[] infoIds) {
        return baseMapper.deleteBatchIds(Arrays.asList(infoIds));
    }

    /**
     * 清空系统登录日志
     */
    @Override
    public void cleanLogininfor() {
        baseMapper.delete(new LambdaQueryWrapper<>());
    }
}
