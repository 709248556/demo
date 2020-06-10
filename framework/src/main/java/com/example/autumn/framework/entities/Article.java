package com.example.autumn.framework.entities;

import com.autumn.domain.entities.AbstractDefaultEntity;
import com.autumn.domain.entities.auditing.SoftDelete;
import com.example.autumn.framework.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import java.util.Date;

/**  
* @Description:
* @author yanlianglong
* @date 2020-6-9
*/
@ToString(callSuper = true)
@Getter
@Setter
@Table(name = "sp_article")
public class Article extends AbstractDefaultEntity implements BaseEntity,SoftDelete{

	private static final long serialVersionUID = 5123038478935570461L ;
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Override
	public Long getId() {
		return super.getId();
	}

	/**
	* id
	*/
    private Long id;

	/**
	* 标题
	*/
	private String title;

	/**
	* 内容类型，1：图文，2：视频
	*/
	private Integer contentType;

	/**
	* 内容概要
	*/
	private String intro;

	/**
	* 详情
	*/
	private String detail;

	/**
	* 封面图id
	*/
	private Long fileId;

	/**
	* 作者
	*/
	private String author;

	/**
	* 是否原创，1：原创，2：非原创
	*/
	private Integer isOriginal;

	/**
	* 类型，1：资讯，2：新闻动态，3：专家讲解，4：辟谣信息
	*/
	private Integer type;

	/**
	* 分类id
	*/
	private Long newsCategoryId;

	/**
	* 活动状态，1：待发布，2：已发布，3：撤回
	*/
	private Integer status;

	/**
	* 是否删除
	*/
	private Integer isDelete;

	/**
	* 
	*/
	private Date createdAt;

	/**
	* 
	*/
	private Date updatedAt;

	/**
     * 是否删除
     */
    private boolean delete;
}
