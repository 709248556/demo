package com.example.autumn.redis.Article;

import com.example.autumn.redis.base.BasePageInput;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**  
* @Description:
* @author yanlianglong
* @date 2020-6-9
*/
@Data
public class ArticleSelectDto extends BasePageInput {

    private static final long serialVersionUID = 6280878174693340559L ;

	/**
	* 内容类型，1：图文，2：视频
	*/
	@ApiModelProperty(value = "内容类型，1：图文，2：视频")
	private Integer contentType;

	/**
	* 活动状态，1：待发布，2：已发布，3：撤回
	*/
	@ApiModelProperty(value = "活动状态，1：待发布，2：已发布，3：撤回")
	private Integer status;

	/**
	* 分类id
	*/
	@ApiModelProperty(value = "分类id")
	private Long newsCategoryId;

	/**
	* 标题
	*/
	@ApiModelProperty(value = "标题")
	private String title;

}
