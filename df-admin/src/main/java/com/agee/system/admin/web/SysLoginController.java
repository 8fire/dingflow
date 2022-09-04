package com.agee.system.admin.web;

import com.agee.common.core.domain.R;
import com.agee.system.admin.model.dto.Login;
import com.agee.system.admin.model.vo.LoginUserInfo;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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


    @ApiOperation(value = "登录接口",notes = "该接口系统用户登录")
    @PostMapping("/doLogin")
    public R<LoginUserInfo> doLogin(@RequestBody Login login){

        return R.ok();
    }

}
