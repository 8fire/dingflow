package com.agee.admin.web.system;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.agee.common.annotation.Log;
import com.agee.common.core.controller.BaseController;
import com.agee.common.core.domain.R;
import com.agee.common.enums.BusinessTypeEnum;
import com.agee.framework.annotation.Idempotent;
import com.agee.framework.service.SecurityUtils;
import com.agee.system.domain.SysMenu;
import com.agee.system.domain.req.SysMenuCreateReq;
import com.agee.system.domain.req.SysMenuUpdateReq;
import com.agee.system.domain.resp.TreeSelectResp;
import com.agee.system.service.SysMenuService;
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
 * @date 2022-09-20 09:03
 * @Description: 菜单权限
 */
@RestController
@RequestMapping("/menu")
@Validated
@Api(tags = "系统菜单权限服务")
@Slf4j
@ApiSort(4)
@RequiredArgsConstructor
public class SysMenuController extends BaseController {

    private final SysMenuService menuService;

    @SaCheckPermission("system:menu:list")
    @GetMapping("/list")
    @ApiOperation(value = "查询菜单列表",notes = "该接口用于查询菜单列表")
    public R<List<SysMenu>> list(SysMenu menu) {
        List<SysMenu> menus = menuService.selectMenuList(menu, SecurityUtils.getLoginUserId());
        return R.ok(menus);
    }

    @SaCheckPermission("system:menu:query")
    @GetMapping(value = "/getInfo/{menuId}")
    @ApiOperation(value = "根据菜单编号获取详细信息",notes = "该接口用于根据菜单编号获取详细信息")
    public R<SysMenu> getInfo(@PathVariable Long menuId) {
        return R.ok(menuService.selectMenuById(menuId));
    }


    @ApiOperation(value = "获取菜单下拉树列表",notes = "该接口用于获取菜单下拉树列表")
    @GetMapping("/treeSelect")
    public R<List<TreeSelectResp>> treeSelect(@RequestBody SysMenu menu) {
        List<SysMenu> menus = menuService.selectMenuList(menu, SecurityUtils.getLoginUserId());
        return R.ok(menuService.buildMenuTreeSelect(menus));
    }



    @SaCheckPermission("system:menu:add")
    @Log(title = "菜单管理", businessType = BusinessTypeEnum.INSERT)
    @PostMapping("/add")
    @Idempotent
    @ApiOperation(value = "新增菜单",notes = "该接口用于新增菜单信息")
    public R<Long> add(@Validated @RequestBody SysMenuCreateReq sysMenuCreateReq) {
        sysMenuCreateReq.setCreateBy(SecurityUtils.getLoginName());
        return R.ok(menuService.insertMenu(sysMenuCreateReq));
    }

    @SaCheckPermission("system:menu:edit")
    @Log(title = "菜单管理", businessType = BusinessTypeEnum.UPDATE)
    @PostMapping("/edit")
    @Idempotent
    @ApiOperation(value = "编辑菜单",notes = "该接口用于编辑菜单信息")
    public R<?> edit(@Validated @RequestBody SysMenuUpdateReq sysMenuUpdateReq) {
        return R.ok(menuService.updateMenu(sysMenuUpdateReq));
    }


    @SaCheckPermission("system:menu:remove")
    @Log(title = "菜单管理", businessType = BusinessTypeEnum.DELETE)
    @GetMapping("/remove/{menuId}")
    @ApiOperation(value = "删除菜单",notes = "该接口用于删除菜单信息")
    public R<Integer> remove(@PathVariable("menuId") Long menuId) {
        if (menuService.hasChildByMenuId(menuId)) {
            return R.fail("存在子菜单,不允许删除");
        }
        if (menuService.checkMenuExistRole(menuId)) {
            return R.fail("菜单已分配,不允许删除");
        }
        return R.ok(menuService.deleteMenuById(menuId));
    }
}
