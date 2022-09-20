package com.agee.admin.web.system;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.agee.common.annotation.Log;
import com.agee.common.core.controller.BaseController;
import com.agee.common.core.domain.R;
import com.agee.common.core.page.TableDataInfo;
import com.agee.common.enums.BusinessType;
import com.agee.framework.annotation.Idempotent;
import com.agee.framework.service.SecurityUtils;
import com.agee.system.domain.SysUser;
import com.agee.system.domain.req.SysUserCreateReq;
import com.agee.system.domain.req.SysUserPageQueryReq;
import com.agee.system.domain.req.SysUserUpdateReq;
import com.agee.system.service.SysUserService;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author qimingjin
 * @date 2022-09-19 11:19
 * @Description: 系统用户服务
 */
@RestController
@RequestMapping("/user")
@Validated
@Api(tags = "系统用户服务")
@Slf4j
@ApiSort(2)
@RequiredArgsConstructor
public class SysUserController extends BaseController {

    private final SysUserService userService;

   // @SaCheckPermission("system:user:list")
    @GetMapping("/getPageList")
    @ApiOperation(value = "分页获取用户",notes = "该接口用于分页获取用户信息")
    public R<TableDataInfo<SysUser>> getPageList(SysUserPageQueryReq sysUserPageQueryReq) {
        startPage();
        List<SysUser> list = userService.selectUserList(sysUserPageQueryReq);
        return getDataTable(list);
    }


    @SaCheckPermission("system:user:add")
    @Log(title = "用户管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @Idempotent
    @ApiOperation(value = "新增用户",notes = "该接口用于新增用户信息")
    public R<Long> addSave(@Validated @RequestBody SysUserCreateReq sysUserCreateReq) {
        sysUserCreateReq.setPassword(SecurityUtils.encryptPassword(sysUserCreateReq.getPassword()));
        sysUserCreateReq.setCreateBy(SecurityUtils.getLoginName());
        return R.ok(userService.insertUser(sysUserCreateReq));
    }

    @SaCheckPermission("system:user:edit")
    @Log(title = "用户管理", businessType = BusinessType.INSERT)
    @PostMapping("/edit")
    @Idempotent
    @ApiOperation(value = "编辑用户",notes = "该接口用于编辑用户信息")
    public R<Long> edit(@Validated @RequestBody SysUserUpdateReq sysUserUpdateReq) {
        sysUserUpdateReq.setUpdateBy(SecurityUtils.getLoginName());
        return R.ok(userService.editUser(sysUserUpdateReq));
    }

    @SaCheckPermission("system:user:remove")
    @Log(title = "用户管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @Idempotent
    @ApiOperation(value = "删除用户",notes = "该接口用于删除用户信息")
    public R<Integer> remove(Long[] ids) {
       return R.ok(userService.deleteUserByIds(ids));
    }


    @SaCheckPermission("system:user:changeStatus")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @GetMapping("/changeStatus")
    @Idempotent
    @ApiOperation(value = "编辑用户状态",notes = "该接口用于编辑用户状态信息")
    public R<Boolean> changeStatus(@RequestParam("userId") Long userId, @RequestParam("status") String status) {
        SysUser user = userService.selectUserById(userId);
        userService.checkUserAllowed(user);
        SysUser sysUser=new SysUser();
        sysUser.setUserId(userId);
        sysUser.setStatus(status);
        return R.ok(userService.updateById(sysUser));
    }

    @SaCheckPermission("system:user:authRole")
    @Log(title = "用户管理", businessType = BusinessType.GRANT)
    @PostMapping("/authRole")
    @ApiOperation(value = "用户角色授权",notes = "该接口用于用户角色授权信息")
    public R<Void> insertAuthRole(Long userId, Long[] roleIds) {
        userService.insertUserRole(userId, roleIds);
        return R.ok();
    }
}
