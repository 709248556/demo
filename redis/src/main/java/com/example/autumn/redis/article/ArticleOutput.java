package com.example.autumn.redis.article;

import lombok.Data;

/**
* @Description:
* @author yanlianglong
* @date 2020-6-5
*/
@Data
public class ArticleOutput extends ArticleInput{

	private static final long serialVersionUID = 8135810702512373864L ;

	private Long size;
}
