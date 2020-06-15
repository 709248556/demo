package com.example.autumn.redis.article;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
* @Description:
* @author yanlianglong
* @date 2020-6-5
*/
@Data
public class ArticleOutput extends ArticleInput{

	private static final long serialVersionUID = 8135810702512373864L ;

	/**
	 * 状态，1：待发布，2：已发布，3：撤回
	 */
	@ApiModelProperty(value = "状态，1：待发布，2：已发布，3：撤回")
	private Integer sendStatus;

	/**
	 * 提审状态，1：草稿，2：提审中，3：撤回
	 */
	@ApiModelProperty(value = "提审状态，1：草稿，2：提审中，3：撤回")
	private Integer submitCheckStatus;

	/**
	 * 是否删除
	 */
	@ApiModelProperty(value = "是否删除")
	private boolean delete;

	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	private Date createdAt;

	/**
	 * 更新时间
	 */
	@ApiModelProperty(value = "更新时间")
	private Date updatedAt;

}
