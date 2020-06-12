package com.example.autumn.redis.article;

import com.autumn.application.dto.EntityDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**  
* @Description: 文章Input
* @author yanlianglong
* @date 2020-6-5
*/
@Data
public class ArticleInput extends EntityDto<Long>{

    private static final long serialVersionUID = 5382452759380211833L ;

	/**
	* 标题
	*/
	@NotEmpty(message = "标题不能为空")
	@Length(max = 20, message = "标题过长")
    @ApiModelProperty(value = "标题")
	private String title;

	/**
	* 内容类型，1：图文，2：视频
	*/
	@NotNull(message = "内容类型，1：图文，2：视频不能为空")
    @ApiModelProperty(value = "内容类型，1：图文，2：视频")
	private Integer contentType;

	/**
	* 内容概要
	*/
	@NotEmpty(message = "内容概要不能为空")
    @ApiModelProperty(value = "内容概要")
	private String intro;

	/**
	* 详情
	*/
    @ApiModelProperty(value = "详情")
	private String detail;

	/**
	* 封面图id
	*/
	@NotNull(message = "封面图id不能为空")
    @ApiModelProperty(value = "封面图id")
	private Long fileId;

	/**
	* 作者
	*/
	@NotEmpty(message = "作者不能为空")
    @ApiModelProperty(value = "作者")
	private String author;

	/**
	* 是否原创，1：原创，2：非原创
	*/
	@NotNull(message = "是否原创不能为空")
    @ApiModelProperty(value = "是否原创，1：原创，2：非原创")
	private Integer isOriginal;

	/**
	* 类型，1：资讯，2：新闻动态，3：专家讲解，4：辟谣信息
	*/
	@NotNull(message = "类型不能为空")
    @ApiModelProperty(value = "类型，1：资讯，2：新闻动态，3：专家讲解，4：辟谣信息")
	private Integer type;

	/**
	* 类别id
	*/
	@NotNull(message = "类别id不能为空")
    @ApiModelProperty(value = "类别id")
	private Long newsCategoryId;

}
