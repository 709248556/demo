package com.example.autumn.framework.base;

import com.autumn.application.dto.EntityDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class BaseIdDto extends EntityDto<Long> {

    @ApiModelProperty(value = "对象id")
    @NotNull(message = "id不能为空")
    @Override
    public Long getId() {
        return super.getId();
    }
}
