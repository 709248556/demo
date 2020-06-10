package com.example.autumn.framework.config;

import com.autumn.cloud.annotation.EnableAutumnCloudLock;
import com.autumn.cloud.annotation.EnableAutumnCloudUidGenerator;
import com.autumn.evaluator.annotation.EnableAutumnEvaluator;
import com.autumn.mybatis.annotation.EnableAutumnMybatis;
import com.autumn.mybatis.annotation.EnableAutumnPerformance;
import com.autumn.mybatis.annotation.EnableTableAutoDefinition;
import com.autumn.redis.annotation.EnableAutumnRedis;
import com.autumn.security.annotation.EnableAutumnSecurity;
import com.autumn.sms.annotation.EnableAutumnSmsChannel;
import com.autumn.web.annotation.EnableNoRepeatSubmit;
import com.autumn.zero.authorization.annotation.*;
import com.autumn.zero.common.library.annotation.EnableAutumnZeroCommonLibrary;
import com.autumn.zero.file.storage.annotation.EnableAutumnZeroFileStorage;
import com.autumn.zero.ueditor.annotation.EnableAutumnUeditorWeb;
import com.example.autumn.framework.constans.WebCommonConstants;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

import java.lang.annotation.*;

/**
 * Web启动配置
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-02 21:21
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan(WebCommonConstants.SCAN_PACKAGE_PATH)
@EnableAutumnMybatis(typeAliasesPackages = {WebCommonConstants.ENTITY_PACKAGE_PATH},
        value = {WebCommonConstants.REPOSITORY_PACKAGE_PATH},
        configLocation = WebCommonConstants.MYBATIS_CONFIG_FILE)
@EnableAutumnCloudUidGenerator  //分布式id生成器
@EnableAutumnRedis                 //启动redis，一般要启动，框架缓存依赖
@EnableAutumnCloudLock             //分布式锁，未用可去掉
@EnableAutumnSmsChannel                //发送短信
@EnableAutumnZeroCommonAuthorization     //公用认证，需启用，提供资源管理、菜单管理，权限管理，若不使用内置可去掉
@EnableAutumnZeroCaptchaAuthorization    //提供验证码，图形验证、短信验证码，如果分别启用可去掉，去配置相关组件
@EnableAutumnSmsAuth                     //短信身份认证，依赖EnableAutumnSmsChannel，不需要启去掉
@EnableAutumnEvaluator                   //表达式解析，打印解析、工作流解析，任何需要解析的地方或使用，不需要可去掉
//@EnableAutumnWord                        //提供 Word 解析 依赖 EnableAutumnEvaluator，不需要启去掉
@EnableAutumnZeroFileStorage               //文件存储，一般需要，提供文件上传与使用的接口，一般均需要，除非项目不使用附件
@EnableAutumnZeroCommonLibrary             //公共库，公共树、行政区、系统配置，可选，或使用特定组件
@EnableAutumnUeditorWeb                    //百度web ueditor 编辑器，客户端不使可去掉
@EnableNoRepeatSubmit                      //防重复提交，不需要可去掉(配置指定的控制器上的api配置才生效，不会拦截所有url)
@EnableAutumnSecurity                      //安全，需要web登录与认证均需要
@EnableAutumnZeroDefaultAuthorization      //使用内置用户系统，一般都要重写，除非是简单的系统，不区分前后端
@EnableCaching                              //启用缓存spring boot 自带，框架引会引用缓存组件。
@EnableAutumnZeroCaptchaAuthorizationWeb    //短信验证码与图形验证码
@EnableTableAutoDefinition                  //启用表自动定义，生产环境不需要
@EnableAutumnPerformance(maxExecuteTime = 1L, writeInLog = false, consolePrint = true)//启用性能监视，生产环境根据需要
public @interface EnableWebStartupConfigureForAdmin {

}
