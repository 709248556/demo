package com.example.autumn.framework.repositories;

import com.autumn.domain.repositories.DefaultEntityRepository;
import com.example.autumn.framework.entities.Article;
import org.springframework.stereotype.Repository;

/**  
* @Description:
* @author yanlianglong
* @date 2020-6-9
*/
@Repository
public interface ArticleRepository extends DefaultEntityRepository<Article>{

}
