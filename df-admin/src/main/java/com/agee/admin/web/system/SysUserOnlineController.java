package com.agee.admin.web.system;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.stp.StpUtil;
import com.agee.common.annotation.Log;
import com.agee.common.core.controller.BaseController;
import com.agee.common.core.domain.R;
import com.agee.common.core.page.TableDataInfo;
import com.agee.common.enums.BusinessTypeEnum;
import com.agee.framework.annotation.Idempotent;
import com.agee.framework.service.AuthService;
import com.agee.system.domain.resp.UserOnlineResp;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author qimingjin
 * @date 2022-09-21 10:07
 * @Description: 系统在线用户服务
 */
@RestController
@RequestMapping("/userOnline")
@Validated
@Api(tags = "系统在线用户服务")
@Slf4j
@ApiSort(5)
@RequiredArgsConstructor
public class SysUserOnlineController extends BaseController {

    private final AuthService authService;

    @SaCheckPermission("monitor:online:list")
    @GetMapping("/list")
    @ApiOperation(value = "分页获取在线用户",notes = "该接口用于分页获取在线用户")
    public R<TableDataInfo<UserOnlineResp>> list(@ApiParam(value = "登录地址") String ipaddr,@ApiParam(value = "用户名") String userName) {
        List<UserOnlineResp> userOnlineList = authService.getUserOnlineList(ipaddr, userName);
        return pageBySubList(userOnlineList);
    }

    /**
     * 强退用户
     *
     * @param tokenId token值
     */
    @SaCheckPermission("monitor:online:forceLogout")
    @Log(title = "在线用户", businessType = BusinessTypeEnum.FORCE)
    @ApiOperation(value = "强退在线用户",notes = "该接口用于强退在线用户")
    @GetMapping("/{tokenId}")
    @Idempotent
    public R<Void> forceLogout(@PathVariable String tokenId) {
        StpUtil.kickoutByTokenValue(tokenId);
        return R.ok();
    }
}
