package com.agee.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.agee.system.domain.SysRole;
import com.agee.system.mapper.SysRoleMapper;
import com.agee.system.service.SysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public Set<String> selectRolePermissionByUserId(Long userId) {
        List<SysRole> perms = sysRoleMapper.selectRolesByUserId(userId);
        if(CollUtil.isEmpty(perms)){
            return CollUtil.newHashSet();
        }
        return perms.stream().map(SysRole::getRoleKey).collect(Collectors.toSet());
    }
}
