package com.agee.admin.web.system;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.agee.common.core.controller.BaseController;
import com.agee.common.core.domain.R;
import com.agee.common.core.page.TableDataInfo;
import com.agee.framework.annotation.Idempotent;
import com.agee.system.domain.SysRole;
import com.agee.system.service.SysRoleService;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.Api;
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
    @Idempotent
    public R<TableDataInfo> list(@RequestBody SysRole role) {
        startPage();
        List<SysRole> list = roleService.selectRoleList(role);
        return getDataTable(list);
    }

    @GetMapping("/getRoleById")
    @Idempotent
    public R<SysRole> getRoleById(@RequestParam(value = "id") Integer id) {
        SysRole list = roleService.getById(id);
        return R.ok(list);
    }
}
