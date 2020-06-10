package com.example.autumn.framework.dto.Article;

import com.autumn.application.dto.EntityDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**  
* @Description:
* @author yanlianglong
* @date 2020-6-9
*/
@Data
public class ArticleInput extends EntityDto<Long>{

    private static final long serialVersionUID = 7923896380018375540L ;

	/**
	* 内容类型，1：图文，2：视频
	*/
	@NotNull(message = "内容类型，1：图文，2：视频不能为空")
	@ApiModelProperty(value = "内容类型，1：图文，2：视频")
	private Integer contentType;
	/**
	* 是否原创，1：原创，2：非原创
	*/
	@NotNull(message = "是否原创，1：原创，2：非原创不能为空")
	@ApiModelProperty(value = "是否原创，1：原创，2：非原创")
	private Integer isOriginal;
	/**
	* 作者
	*/
	@NotEmpty(message = "作者不能为空")
		@ApiModelProperty(value = "作者")
	private String author;
	/**
	* 内容概要
	*/
	@NotEmpty(message = "内容概要不能为空")
		@ApiModelProperty(value = "内容概要")
	private String intro;
	/**
	* 封面图id
	*/
	@NotNull(message = "封面图id不能为空")
		@ApiModelProperty(value = "封面图id")
	private Long fileId;
	/**
	* 详情
	*/
	@ApiModelProperty(value = "详情")
	private String detail;
	/**
	* 标题
	*/
	@NotEmpty(message = "标题不能为空")
		@ApiModelProperty(value = "标题")
	private String title;
}
