package com.agee.system.service.impl;

import com.agee.system.domain.SysRole;
import com.agee.system.mapper.SysRoleMapper;
import com.agee.system.service.SysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author qimingjin
 * @date 2022-09-14 16:14
 * @Description: 角色
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole>  implements SysRoleService {
    @Override
    public Set<String> selectRolePermissionByUserId(Long userId) {
        return null;
    }
}
