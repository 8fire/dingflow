package com.agee.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.agee.common.core.constant.Constants;
import com.agee.common.enums.ResponseCodeEnum;
import com.agee.common.exception.ServiceException;
import com.agee.common.utils.StringUtils;
import com.agee.system.domain.SysMenu;
import com.agee.system.domain.SysRoleMenu;
import com.agee.system.domain.req.SysMenuCreateReq;
import com.agee.system.domain.req.SysMenuUpdateReq;
import com.agee.system.domain.resp.TreeSelectResp;
import com.agee.system.mapper.SysMenuMapper;
import com.agee.system.mapper.SysRoleMenuMapper;
import com.agee.system.service.SysMenuService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author qimingjin
 * @date 2022-09-14 16:14
 * @Description:
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private SysRoleMenuMapper roleMenuMapper;

    @Override
    public List<SysMenu> selectMenuList(SysMenu menu, Long userId) {
        return null;
    }

    @Override
    public Long insertMenu(SysMenuCreateReq sysMenuCreateReq) {
        if (checkMenuNameUnique(null,sysMenuCreateReq.getMenuName())) {
            throw new ServiceException(ResponseCodeEnum.MENU_NAME_EXIST_ERROR);
        } else if (Constants.YES_FRAME.equals(sysMenuCreateReq.getIsFrame()) && !StringUtils.ishttp(sysMenuCreateReq.getPath())) {
            throw new ServiceException(ResponseCodeEnum.MENU_ADDRESS_ERROR);
        }
        SysMenu sysMenu = BeanUtil.toBean(sysMenuCreateReq, SysMenu.class);
        baseMapper.insert(sysMenu);
        return sysMenu.getMenuId();
    }

    @Override
    public Long updateMenu(SysMenuUpdateReq sysMenuUpdateReq) {
        if (checkMenuNameUnique(sysMenuUpdateReq.getMenuId(),sysMenuUpdateReq.getMenuName())) {
            throw new ServiceException(ResponseCodeEnum.MENU_NAME_EXIST_ERROR);
        } else if (StrUtil.isBlank(sysMenuUpdateReq.getIsFrame())&&Constants.YES_FRAME.equals(sysMenuUpdateReq.getIsFrame()) && !StringUtils.ishttp(sysMenuUpdateReq.getPath())) {
            throw new ServiceException(ResponseCodeEnum.MENU_ADDRESS_ERROR);
        }else if (sysMenuUpdateReq.getMenuId().equals(sysMenuUpdateReq.getParentId())) {
            throw new ServiceException(ResponseCodeEnum.MENU_PARENT_ERROR);
        }
        SysMenu sysMenu = BeanUtil.toBean(sysMenuUpdateReq, SysMenu.class);
        baseMapper.updateById(sysMenu);
        return sysMenu.getMenuId();
    }

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

    @Override
    public boolean hasChildByMenuId(Long menuId) {
        return baseMapper.exists(new LambdaQueryWrapper<SysMenu>().eq(SysMenu::getParentId, menuId));
    }

    @Override
    public boolean checkMenuExistRole(Long menuId) {
        return roleMenuMapper.exists(new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getMenuId, menuId));
    }

    @Override
    public int deleteMenuById(Long menuId) {
        return baseMapper.deleteById(menuId);
    }

    @Override
    public SysMenu selectMenuById(Long menuId) {
        return baseMapper.selectById(menuId);
    }

    @Override
    public List<TreeSelectResp> buildMenuTreeSelect(List<SysMenu> menus) {
        List<SysMenu> menuTrees = buildMenuTree(menus);
        return menuTrees.stream().map(TreeSelectResp::new).collect(Collectors.toList());
    }


    private boolean checkMenuNameUnique(Long menuId,String menuName){
       return sysMenuMapper.exists(Wrappers.<SysMenu>lambdaQuery().ne(ObjectUtil.isNotEmpty(menuId),SysMenu::getMenuId,menuId).eq(SysMenu::getMenuName,menuName));
    }

    /**
     * 构建前端所需要树结构
     *
     * @param menus 菜单列表
     * @return 树结构列表
     */
    @Override
    public List<SysMenu> buildMenuTree(List<SysMenu> menus) {
        List<SysMenu> returnList = new ArrayList<>();
        List<Long> tempList = new ArrayList<>();
        for (SysMenu dept : menus) {
            tempList.add(dept.getMenuId());
        }
        for (Iterator<SysMenu> iterator = menus.iterator(); iterator.hasNext();) {
            SysMenu menu = (SysMenu) iterator.next();
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(menu.getParentId())) {
                recursionFn(menus, menu);
                returnList.add(menu);
            }
        }
        if (returnList.isEmpty()) {
            returnList = menus;
        }
        return returnList;
    }

    /**
     * 递归列表
     *
     * @param list
     * @param t
     */
    private void recursionFn(List<SysMenu> list, SysMenu t) {
        // 得到子节点列表
        List<SysMenu> childList = getChildList(list, t);
        t.setChildren(childList);
        for (SysMenu tChild : childList) {
            if (hasChild(list, tChild)) {
                recursionFn(list, tChild);
            }
        }
    }


    /**
     * 得到子节点列表
     */
    private List<SysMenu> getChildList(List<SysMenu> list, SysMenu t) {
        List<SysMenu> tlist = new ArrayList<>();
        Iterator<SysMenu> it = list.iterator();
        while (it.hasNext()) {
            SysMenu n = (SysMenu) it.next();
            if (n.getParentId().longValue() == t.getMenuId().longValue()) {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysMenu> list, SysMenu t) {
        return getChildList(list, t).size() > 0;
    }

    /**
     * 内链域名特殊字符替换
     *
     * @return
     */
    public String innerLinkReplaceEach(String path) {
        return StringUtils.replaceEach(path, new String[] { Constants.HTTP, Constants.HTTPS },
                new String[] { "", "" });
    }
}
