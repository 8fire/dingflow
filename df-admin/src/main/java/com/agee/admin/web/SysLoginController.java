package com.agee.admin.web;

import cn.dev33.satoken.stp.SaTokenInfo;
import com.agee.admin.model.dto.Login;
import com.agee.common.core.domain.R;
import com.agee.framework.service.AuthService;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
public class SysLoginController {

    @Autowired
    private AuthService authService;

    @ApiOperation(value = "登录接口",notes = "该接口系统用户登录")
    @PostMapping("/doLogin")
    public R<SaTokenInfo> doLogin(@RequestBody Login login){
        return R.ok( authService.doLogin(login.getUserName(),login.getPassword()));
    }

}
