package com.agee.admin.web.system;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.agee.common.annotation.Log;
import com.agee.common.core.controller.BaseController;
import com.agee.common.core.domain.R;
import com.agee.common.core.page.TableDataInfo;
import com.agee.common.enums.BusinessType;
import com.agee.framework.annotation.Idempotent;
import com.agee.system.domain.SysRole;
import com.agee.system.service.SysRoleService;
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
 * @date 2022-09-16 11:04
 * @Description: 系统角色服务
 */
@RestController
@RequestMapping("/role")
@Validated
@Api(tags = "系统角色服务")
@Slf4j
@ApiSort(1)
@RequiredArgsConstructor
public class SysRoleController extends BaseController {

    private final SysRoleService roleService;

    //@SaCheckPermission("system:role:list")
    @PostMapping("/list")
    @ApiOperation(value = "分页查询角色列表",notes = "该接口用于分页获取角色列表信息")
    public R<TableDataInfo<SysRole>> list(@RequestBody SysRole role) {
        startPage();
        List<SysRole> list = roleService.selectPageRoleList(role);
        return getDataTable(list);
    }


    @SaCheckPermission("system:role:add")
    @Log(title = "角色管理", businessType = BusinessType.INSERT)
    @ApiOperation(value = "新增角色",notes = "该接口用于新增角色信息")
    @PostMapping
    public R<Long> add(@Validated @RequestBody SysRole role) {
        return R.ok(roleService.insertRole(role));
    }

    @SaCheckPermission("system:role:edit")
    @Log(title = "角色管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public R<?> edit(@Validated @RequestBody SysRole role) {
        roleService.updateRole(role);
        return R.ok();
    }

    @GetMapping("/getRoleById")
    @Idempotent
    public R<SysRole> getRoleById(@RequestParam(value = "id") Integer id) {
        SysRole list = roleService.getById(id);
        return R.ok(list);
    }
}
