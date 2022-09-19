package com.agee.admin.web.system;

import cn.dev33.satoken.stp.SaTokenInfo;
import com.agee.common.core.domain.Login;
import com.agee.common.core.domain.R;
import com.agee.framework.service.AuthService;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @program: df
 * @description
 * @author: 没用的阿吉
 * @create: 2022-09-03 10:44
 **/
@RestController
@RequestMapping("/auth")
@Validated
@Api(tags = "登录鉴权服务")
@Slf4j
@ApiSort(1)
@RequiredArgsConstructor
public class SysLoginController {

    private final AuthService authService;

    @ApiOperation(value = "登录接口",notes = "该接口系统用户登录")
    @PostMapping("/doLogin")
    public R<SaTokenInfo> doLogin(@Valid @RequestBody Login login){
        return R.ok(authService.doLogin(login.getUserName(),login.getPassword(),login.getCode(),login.getCaptchaId()));
    }

    @ApiOperation(value = "重置密码", notes = "该接口用户重置用户密码")
    @PostMapping("/resetPwd")
    @ResponseBody
    public R<Void> resetPwd(@ApiParam(value = "老密码（经过Md5 32位小写加密）") @RequestParam(value = "oldPassword") String oldPassword,
                         @ApiParam(value = "新密码（经过Md5 32位小写加密）") @RequestParam(value = "newPassword")   String newPassword) {
        authService.resetPwd(oldPassword,newPassword);
        return R.ok();
    }

}
