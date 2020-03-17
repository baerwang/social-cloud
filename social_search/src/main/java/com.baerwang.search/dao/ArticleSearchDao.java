package com.baerwang.search.dao;

import com.baerwang.search.pojo.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * 数据访问层
 *
 * @author baerwang
 * @version 1.0
 * @date 2020/2/19 21:13
 */
public interface ArticleSearchDao extends ElasticsearchRepository<Article, String> {
    /**
     * 检索
     *
     * @param title
     * @param content
     * @param pageable
     * @return
     */
    Page<Article> findByTitleOrContentLike(String title, String content, Pageable pageable);

}
