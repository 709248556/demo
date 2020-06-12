package com.example.autumn.redis.article;

import com.example.autumn.redis.base.BasePageInput;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**  
* @Description:
* @author yanlianglong
* @date 2020-6-5
*/
@Data
public class ArticleSelectDto extends BasePageInput {

    private static final long serialVersionUID = 314779407832466741L ;

    /**
     * 内容类型，1：图文，2：视频
     */
    @ApiModelProperty(value = "内容类型，1：图文，2：视频")
    private Integer contentType;

    /**
     * 分类id
     */
    @ApiModelProperty(value = "所属专栏")
    private Long newsCategoryId;


    /**
     * 标题
     */
    @ApiModelProperty(value = "标题")
    private String title;


    /**
     * 发布状态，1：待发布，2：已发布，3：撤回
     */
    @ApiModelProperty(value = "发布状态，1：待发布，2：已发布，3：撤回")
    private Integer sendStatus;

    /**
     * 提审状态，1：草稿，2：提审中，3：撤回
     */
    @ApiModelProperty(value = "提审状态，1：草稿，2：提审中，3：撤回")
    private Integer checkStatus;

}
