package com.agee.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.agee.system.domain.SysMenu;
import com.agee.system.mapper.SysMenuMapper;
import com.agee.system.service.SysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author qimingjin
 * @date 2022-09-14 16:14
 * @Description:
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public Set<String> selectMenuPermsByUserId(Long userId) {
        List<String> perms =   sysMenuMapper.selectMenuPermsByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms) {
            if (StrUtil.isNotEmpty(perm)) {
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        return permsSet;
    }

    @Override
    public Set<String> selectMenuPermsByRoleId(Long roleId) {
        return null;
    }
}
