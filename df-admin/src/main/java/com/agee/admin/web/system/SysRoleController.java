package com.agee.admin.web.system;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.agee.common.annotation.Log;
import com.agee.common.constant.Constants;
import com.agee.common.core.controller.BaseController;
import com.agee.common.core.domain.R;
import com.agee.common.core.page.TableDataInfo;
import com.agee.common.enums.BusinessTypeEnum;
import com.agee.framework.annotation.Idempotent;
import com.agee.system.domain.SysRole;
import com.agee.system.domain.req.SysRoleCreateReq;
import com.agee.system.domain.req.SysRoleUpdateReq;
import com.agee.system.service.SysRoleService;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
@ApiSort(3)
@RequiredArgsConstructor
public class SysRoleController extends BaseController {

    private final SysRoleService roleService;

    //@SaCheckPermission("system:role:list")
    @PostMapping("/list")
    @ApiOperation(value = "分页查询角色列表",notes = "该接口用于分页获取角色列表信息")
    public R<TableDataInfo<SysRole>> list(@RequestBody SysRole role) {
        startPage();
        role.setRoleType(Constants.SYSTEM_ROLE_TYPE);
        List<SysRole> list = roleService.selectPageRoleList(role);
        return getDataTable(list);
    }


 //   @SaCheckPermission("system:role:add")
    @Log(title = "角色管理", businessType = BusinessTypeEnum.INSERT)
    @ApiOperation(value = "新增角色",notes = "该接口用于新增角色信息")
    @Idempotent
    @PostMapping("/add")
    public R<Long> add(@Valid @RequestBody SysRoleCreateReq roleCreateReq) {
        return R.ok(roleService.insertRole(roleCreateReq));
    }

    @SaCheckPermission("system:role:edit")
    @Log(title = "角色管理", businessType = BusinessTypeEnum.UPDATE)
    @Idempotent
    @PostMapping("/edit")
    @ApiOperation(value = "编辑角色",notes = "该接口用于编辑角色信息")
    public R<?> edit(@Validated @RequestBody SysRoleUpdateReq role) {
        roleService.updateRole(role);
        return R.ok();
    }

    @SaCheckPermission("system:role:remove")
    @Log(title = "角色管理", businessType = BusinessTypeEnum.DELETE)
    @DeleteMapping("/remove/{roleIds}")
    @ApiOperation(value = "删除角色",notes = "该接口用于删除角色信息")
    public R<Integer> remove(@PathVariable Long[] roleIds) {
        return R.ok(roleService.deleteRoleByIds(roleIds));
    }


    @SaCheckPermission("system:role:saveAuthUsers")
    @Log(title = "角色管理", businessType = BusinessTypeEnum.GRANT)
    @PutMapping("/saveAuthUsers")
    @ApiOperation(value = "绑定用户角色",notes = "该接口用于绑定用户角色信息")
    public R<Integer> saveAuthUsers(Long roleId, Long[] userIds) {
        return R.ok(roleService.insertAuthUsers(roleId, userIds));
    }
}
