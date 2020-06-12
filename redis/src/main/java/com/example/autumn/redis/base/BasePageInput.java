package com.example.autumn.redis.base;

import com.autumn.annotation.FriendlyProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
public class BasePageInput implements Serializable {

    @ApiModelProperty(value = "当前页")
    @FriendlyProperty(value = "当前页")
    private Integer currentPage;

    @ApiModelProperty(value = "页大小")
    @FriendlyProperty(value = "页大小")
    private Integer pageSize;

    public int getCurrentPage() {
        if (currentPage == null) {
            return 1;
        }
        return currentPage;
    }

    public int getPageSize() {
        if (pageSize == null) {
            return 10;
        }
        if (pageSize > 200) {
            return 200;
        }
        return pageSize;
    }

    public boolean isLegal(Object id) {
        Pattern compile = Pattern.compile("^[1-9]\\d*|0$");
        Matcher matcher = compile.matcher(String.valueOf(id));
        return matcher.matches();
    }
}
