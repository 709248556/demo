package com.example.autumn.redis.controller;


import com.autumn.mybatis.mapper.PageResult;
import com.example.autumn.redis.article.ArticleInput;
import com.example.autumn.redis.article.ArticleOutput;
import com.example.autumn.redis.article.ArticleSelectDto;
import com.example.autumn.redis.base.BaseIdDto;
import com.example.autumn.redis.service.ArticleService;
import com.example.autumn.redis.service.RedisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/article")
@Api(tags = "文章管理")
@RequiresUser
public class ArticleController {

	@Autowired
	private ArticleService articleService;
	@Autowired
    private RedisService redisService;

    @ApiOperation(value = "添加文章信息")
    @ApiResponses(value = {@ApiResponse(code = 1000, message = "OK")})
    @PostMapping("/add")
    public ArticleOutput add(@Valid @RequestBody ArticleInput articleInput){
        return articleService.add(articleInput);
    }

    @ApiOperation(value = "编辑文章信息")
    @ApiResponses(value = {@ApiResponse(code = 1000, message = "OK")})
    @PostMapping("/update")
    public ArticleOutput update(@Valid @RequestBody ArticleInput articleInput){
        return articleService.update(articleInput);
    }
    @ApiOperation(value = "删除文章信息")
    @ApiResponses(value = {@ApiResponse(code = 1000, message = "OK")})
    @PostMapping("/deleteById")
    public ArticleOutput update(@Valid @RequestBody BaseIdDto input){
        return articleService.deleteById(input);
    }

    @ApiOperation(value = "获取文章信息详情")
    @ApiResponses(value = {@ApiResponse(code = 1000, message = "OK")})
    @GetMapping("/queryById")
    public ArticleOutput queryById(@Valid BaseIdDto input) {
        return articleService.queryById(input.getId());
    }

    @ApiOperation(value = "获取文章信息列表")
    @ApiResponses(value = {@ApiResponse(code = 1000, message = "OK")})
    @GetMapping("/list")
    public PageResult<ArticleOutput> queryListForCusPage(@Valid ArticleSelectDto input) {
    return articleService.queryListPage(input);
    }

}