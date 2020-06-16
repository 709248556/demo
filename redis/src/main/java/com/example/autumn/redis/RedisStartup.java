package com.example.autumn.redis;


import com.autumn.swagger.annotation.ApiGroup;
import com.autumn.swagger.annotation.ApiHeaderParameter;
import com.autumn.swagger.annotation.EnableAutumnSwagger;
import com.autumn.web.annotation.EnableAutumnApiResponseBody;
import com.autumn.zero.authorization.annotation.*;
import com.autumn.zero.common.library.annotation.EnableAutumnZeroDevCommonLibraryModuleMenu;
import com.autumn.zero.common.library.web.annotation.EnableAutumnZeroAdminCommonLibraryWeb;
import com.example.autumn.redis.config.AbstractWebStartup;
import com.example.autumn.redis.config.EnableWebStartupConfigureForAdmin;
import com.example.autumn.redis.constans.ControllerConstant;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @description:启动类
 * @author: yanlianglong
 * @create: 2020-06-09 10:10
 **/
@EnableWebStartupConfigureForAdmin

@EnableAutumnApiResponseBody({ControllerConstant.CONTROLLER_PACKAGE_PATH})

@EnableAutumnSwagger(
        title = "API 文档",
        description = "",
        authorName = "颜良龙",
        authorUrl = "",
        authorEmail = "709248556@qq.com",
        enableAuthorize = false,
        //3.1.9开启
        headerParameters = {
                @ApiHeaderParameter(name = "token", description = "token"),
                @ApiHeaderParameter(name = "deviceId", description = "设备id")
        },
        groups = {
                @ApiGroup(groupName = "管理系统", packages = ControllerConstant.CONTROLLER_PACKAGE_PATH),
        }
)

@EnableAutumnZeroUrlPermissionInterceptor  //权限拦截，实现url权限配置
@EnableAutumnZeroAdminAuthorizationWeb  //管理端授权Web，后端资源管理、权限、日志的webApi等
@EnableAutumnZeroAdminCommonLibraryWeb  //管理端公共Web，后端的公共库管理、如系统配置、协议
@EnableAutumnZeroAuthAuthorizationWeb    //基本登录 用户名密码登录、注销
@EnableAutumnZeroSmsAuthAuthorizationWeb  //短信登录 提供短信登录

//以下注解仅限首次启动项目需启用生成框架默认表
@EnableAutumnZeroDevAuthorizationResourcesDefinition  //启用授权资源定义，开发时首次启动启用，主要用于后端，正式不要启用
@EnableAutumnZeroDevCommonAuthorizationModuleMenu     //启用共用授权资源与菜单的生成 ，开发时首次启动启用，主要用于后端，正式不要启用
@EnableAutumnZeroDevCommonLibraryModuleMenu    //启用公共库资源与菜单的生成 ，开发时首次启动启用，主要用于后端，正式不要启用

@EnableScheduling                                //定时任务
public class RedisStartup extends AbstractWebStartup {
        /**
         * 启动
         *
         * @param args
         */
        public static void main(String[] args) {
                run(RedisStartup.class, args);
        }
}
