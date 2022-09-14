package com.agee.system.service.impl;

import com.agee.system.domain.SysMenu;
import com.agee.system.mapper.SysMenuMapper;
import com.agee.system.service.SysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.Set;

/**
 * @author qimingjin
 * @date 2022-09-14 16:14
 * @Description:
 */
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {
    @Override
    public Set<String> selectMenuPermsByUserId(Long userId) {
        return null;
    }

    @Override
    public Set<String> selectMenuPermsByRoleId(Long roleId) {
        return null;
    }
}
