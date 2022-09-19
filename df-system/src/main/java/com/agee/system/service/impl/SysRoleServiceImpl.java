package com.agee.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import com.agee.common.core.constant.Constants;
import com.agee.common.enums.ResponseCodeEnum;
import com.agee.common.exception.ServiceException;
import com.agee.system.domain.SysRole;
import com.agee.system.domain.SysRoleMenu;
import com.agee.system.domain.SysUserRole;
import com.agee.system.mapper.SysRoleMapper;
import com.agee.system.mapper.SysRoleMenuMapper;
import com.agee.system.mapper.SysUserRoleMapper;
import com.agee.system.service.SysRoleService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author qimingjin
 * @date 2022-09-14 16:14
 * @Description: 角色
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole>  implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;


    @Override
    public List<SysRole> selectPageRoleList(SysRole sysRole) {
        return sysRoleMapper.selectRoleList(sysRole);
    }

    @Override
    public Long insertRole(SysRole sysRole) {
        if(checkRoleName(null,sysRole.getRoleName())){
            throw new ServiceException(ResponseCodeEnum.ROLE_NAME_EXIST_ERROR);
        }
        if(checkRoleKey(null,sysRole.getRoleKey())){
            throw new ServiceException(ResponseCodeEnum.ROLE_KEY_EXIST_ERROR);
        }
        sysRoleMapper.insertRole(sysRole);
        //新增角色菜单关系
        insertRoleMenu(sysRole);
        return sysRole.getRoleId();
    }

    @Override
    public void updateRole(SysRole role) {
        SysRole sysRole = sysRoleMapper.selectById(role.getRoleId());
        if(Constants.BUILT_IN_STATUS.equals(sysRole.getBuiltIn())){
            throw new ServiceException(ResponseCodeEnum.ROLE_BUILT_IN_ERROR);
        }
        if(checkRoleName(role.getRoleId(),role.getRoleName())){
            throw new ServiceException(ResponseCodeEnum.ROLE_NAME_EXIST_ERROR);
        }
        if(checkRoleKey(role.getRoleId(),role.getRoleKey())){
            throw new ServiceException(ResponseCodeEnum.ROLE_KEY_EXIST_ERROR);
        }
        sysRoleMapper.updateById(role);
        //删除角色菜单关系
        sysRoleMenuMapper.deleteRoleMenuByRoleId(role.getRoleId());
        insertRoleMenu(role);
    }

    @Override
    public int deleteRoleByIds(Long[] roleIds) {
        for (Long roleId : roleIds) {
            SysRole sysRole = sysRoleMapper.selectById(roleId);
            if(Constants.BUILT_IN_STATUS.equals(sysRole.getBuiltIn())){
                throw new ServiceException(ResponseCodeEnum.ROLE_BUILT_IN_ERROR);
            }
            boolean exists = sysUserRoleMapper.exists(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getRoleId, roleId));
            if (exists) {
                throw new ServiceException(ResponseCodeEnum.ROLE_ALLOT_ERROR);
            }
        }
        List<Long> ids = Arrays.asList(roleIds);
        // 删除角色与菜单关联
        sysRoleMenuMapper.delete(new LambdaQueryWrapper<SysRoleMenu>().in(SysRoleMenu::getRoleId, ids));
        // todo 删除角色与部门关联

        return baseMapper.deleteBatchIds(ids);
    }


    @Override
    public Set<String> selectRolePermissionByUserId(Long userId) {
        List<SysRole> perms = sysRoleMapper.selectRolesByUserId(userId);
        if(CollUtil.isEmpty(perms)){
            return CollUtil.newHashSet();
        }
        return perms.stream().map(SysRole::getRoleKey).collect(Collectors.toSet());
    }

    /**
     * 绑定角色菜单关系
     * @param role 角色实体
     */
    private void insertRoleMenu(SysRole role){
        Long[] menuIds = role.getMenuIds();
        if(ArrayUtil.isEmpty(menuIds)){
            return;
        }
        for(Long menuId:menuIds){
            SysRoleMenu sysRoleMenu=new SysRoleMenu();
            sysRoleMenu.setMenuId(menuId);
            sysRoleMenu.setRoleId(role.getRoleId());
            sysRoleMenuMapper.insert(sysRoleMenu);
        }
    }

    private boolean checkRoleName(Long roleId,String roleName){
        return sysRoleMapper.exists(new LambdaQueryWrapper<SysRole>()
                .ne(ObjectUtil.isNotNull(roleId),SysRole::getRoleId,roleId)
                .eq(SysRole::getRoleName,roleName));
    }

    private boolean checkRoleKey(Long roleId,String roleKey){
        return sysRoleMapper.exists(new LambdaQueryWrapper<SysRole>()
                .ne(ObjectUtil.isNotNull(roleId),SysRole::getRoleId,roleId)
                .eq(SysRole::getRoleKey,roleKey));
    }
}
