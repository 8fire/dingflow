package com.agee.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import com.agee.common.bcrypt.BCryptPasswordEncoder;
import com.agee.common.enums.ResponseCodeEnum;
import com.agee.common.exception.ServiceException;
import com.agee.system.domain.SysUser;
import com.agee.system.domain.SysUserRole;
import com.agee.system.domain.req.SysUserCreateReq;
import com.agee.system.domain.req.SysUserPageQueryReq;
import com.agee.system.domain.req.SysUserUpdateReq;
import com.agee.system.mapper.SysUserMapper;
import com.agee.system.mapper.SysUserRoleMapper;
import com.agee.system.service.SysUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author qimingjin
 * @date 2022-09-09 16:36
 * @Description:
 */
@Service
@Slf4j
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper,SysUser> implements SysUserService  {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public List<SysUser> selectUserList(SysUserPageQueryReq sysUserPageQueryReq) {
        return sysUserMapper.selectUserList(BeanUtil.toBean(sysUserPageQueryReq,SysUser.class));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long insertUser(SysUserCreateReq user) {
        if (checkLoginName(null,user.getLoginName())) {
            log.info("@@SysUserServiceImpl->insertUser新增用户时,用户登录名{}已存在",user.getLoginName());
            throw new ServiceException(ResponseCodeEnum.LOGIN_USER_EXIST_ERROR);
        }
        else if (checkUserName(null,user.getUserName())) {
            log.info("@@SysUserServiceImpl->insertUser新增用户时,昵称{}已存在",user.getUserName());
            throw new ServiceException(ResponseCodeEnum.USER_NAME_EXIST_ERROR);
        }
        else if (checkEmail(null,user.getEmail())) {
            log.info("@@SysUserServiceImpl->insertUser新增用户时,邮箱{}已存在",user.getEmail());
            throw new ServiceException(ResponseCodeEnum.USER_EMAIL_EXIST_ERROR);
        }
        else if (checkPhone(null,user.getPhonenumber())) {
            log.info("@@SysUserServiceImpl->insertUser新增用户时,手机号{}已存在",user.getPhonenumber());
            throw new ServiceException(ResponseCodeEnum.USER_PHONE_EXIST_ERROR);
        }
        SysUser sysUser = BeanUtil.toBean(user, SysUser.class);
        sysUser.setSalt(new BCryptPasswordEncoder().getSalt());
        // 新增用户信息
        baseMapper.insert(sysUser);
        // todo 新增用户岗位关联
        // 新增用户与角色管理
        insertUserRole(sysUser.getUserId(), user.getRoleIds());
        return sysUser.getUserId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long editUser(SysUserUpdateReq sysUserUpdateReq) {
        Long userId = sysUserUpdateReq.getUserId();
        if (checkUserName(userId,sysUserUpdateReq.getUserName())) {
            log.info("@@SysUserServiceImpl->editUser编辑用户时,昵称{}已存在",sysUserUpdateReq.getUserName());
            throw new ServiceException(ResponseCodeEnum.USER_NAME_EXIST_ERROR);
        }
        else if (checkEmail(userId,sysUserUpdateReq.getEmail())) {
            log.info("@@SysUserServiceImpl->editUser编辑用户时,邮箱{}已存在",sysUserUpdateReq.getEmail());
            throw new ServiceException(ResponseCodeEnum.USER_EMAIL_EXIST_ERROR);
        }
        else if (checkPhone(userId,sysUserUpdateReq.getPhonenumber())) {
            log.info("@@SysUserServiceImpl->editUser编辑用户时,手机号{}已存在",sysUserUpdateReq.getPhonenumber());
            throw new ServiceException(ResponseCodeEnum.USER_PHONE_EXIST_ERROR);
        }
        //删除用户角色
        sysUserRoleMapper.deleteUserRoleByUserId(userId);
        //保存用户角色关系
        insertUserRole(userId,sysUserUpdateReq.getRoleIds());
        //todo 岗位操作
        baseMapper.updateById(BeanUtil.toBean(sysUserUpdateReq,SysUser.class));
        return userId;
    }

    @Override
    public SysUser selectUserByLoginName(String userName) {
        return sysUserMapper.selectUserByLoginName(userName);
    }

    @Override
    public int deleteUserById(Long userId) {
        // 删除用户与角色关联
        sysUserRoleMapper.deleteUserRoleByUserId(userId);
        //todo 删除用户与岗位表
       // userPostMapper.deleteUserPostByUserId(userId);
        return baseMapper.deleteUserById(userId);
    }

    @Override
    public int deleteUserByIds(Long[] userIds) {
        for (Long userId : userIds) {
            checkUserAllowed(new SysUser(userId));
        }
        return baseMapper.deleteUserByIds(userIds);
    }

    /**
     * 校验用户是否允许操作
     *
     * @param user 用户信息
     */
    @Override
    public void checkUserAllowed(SysUser user) {
        if (ObjectUtil.isNotNull(user.getUserId()) && user.isAdmin()) {
            throw new ServiceException(ResponseCodeEnum.ACCOUNT_UPDATE_SUPER_ERROR);
        }
    }

    @Override
    public SysUser selectUserById(Long userId) {
        return sysUserMapper.selectUserById(userId);
    }

    /**
     * 保存用户角色关系
     * @param userId 用户id
     * @param roleIds 角色集合
     */
    @Override
    public void insertUserRole(Long userId, Long[] roleIds){
        if(ArrayUtil.isEmpty(roleIds)){
            return;
        }
        List<SysUserRole> list = Arrays.stream(roleIds).map(roleId -> new SysUserRole(userId,roleId)).collect(Collectors.toList());
        if (list.size() > 0) {
            sysUserRoleMapper.batchUserRole(list);
        }
    }

    public boolean checkLoginName(Long userId,String LoginName){
        return sysUserMapper.exists(new LambdaQueryWrapper<SysUser>()
                .ne(ObjectUtil.isNotNull(userId),SysUser::getUserId,userId)
                .eq(SysUser::getLoginName,LoginName));
    }

    public boolean checkUserName(Long userId,String userName){
        return sysUserMapper.exists(new LambdaQueryWrapper<SysUser>()
                .ne(ObjectUtil.isNotNull(userId),SysUser::getUserId,userId)
                .eq(SysUser::getUserName,userName));
    }

    public boolean checkEmail(Long userId,String email){
        return sysUserMapper.exists(new LambdaQueryWrapper<SysUser>()
                .ne(ObjectUtil.isNotNull(userId),SysUser::getUserId,userId)
                .eq(SysUser::getEmail,email));
    }

    public boolean checkPhone(Long userId,String phone){
        return sysUserMapper.exists(new LambdaQueryWrapper<SysUser>()
                .ne(ObjectUtil.isNotNull(userId),SysUser::getUserId,userId)
                .eq(SysUser::getPhonenumber,phone));
    }
}
