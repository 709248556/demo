package com.example.autumn.redis.article;

import com.autumn.application.dto.EntityDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**  
* @Description:
* @author yanlianglong
* @date 2020-6-5
*/
@Data
public class PushArticleInput extends EntityDto<Long>{

    private static final long serialVersionUID = 5382452759389211833L ;

	@NotNull(message = "栏目Id不能为空")
	@ApiModelProperty(value = "栏目Id")
	private Long newsCategoryId;
}
