package com.example.autumn.redis.controller;

import com.example.autumn.redis.article.ArticleInput;
import com.example.autumn.redis.service.RedisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description:
 * @author: yanlianglong
 * @create: 2020-06-12 11:25
 **/
@Slf4j
@RestController
@RequestMapping("/redis")
@Api(tags = "redis管理")
@RequiresUser
public class RedisController {

    @Autowired
    private RedisService redisService;

    @ApiOperation(value = "zSet")
    @ApiResponses(value = {@ApiResponse(code = 1000, message = "OK")})
    @PostMapping("/zSet")
    public String set(@RequestBody ArticleInput articleInput){
        redisService.zSetOrderById("abcd",articleInput,articleInput::getId);
        return "ok";
    }

    @ApiOperation(value = "size")
    @ApiResponses(value = {@ApiResponse(code = 1000, message = "OK")})
    @GetMapping("/size")
    public Long size(String id){
        return redisService.size(id);
    }

    @ApiOperation(value = "zSetList")
    @ApiResponses(value = {@ApiResponse(code = 1000, message = "OK")})
    @GetMapping("/zSetList")
    public String redis(@RequestBody List<ArticleInput> articleInputs){
        redisService.zSetListOrderById("abcd",articleInputs);
        return "ok";
    }
}
