package com.baerwang.search.service;

import com.baerwang.search.dao.ArticleSearchDao;
import com.baerwang.search.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 服务层
 *
 * @author baerwang
 * @version 1.0
 * @date 2020/2/19 21:14
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ArticleSearchService {

    @Autowired
    private ArticleSearchDao articleSearchDao;

    /**
     * 增加文章
     *
     * @param article
     */
    public void add(Article article) {
        articleSearchDao.save(article);
    }

    /**
     * 检索+分页
     *
     * @param keyWords
     * @param page
     * @param size
     * @return
     */
    public Page<Article> findByTitleLike(String keyWords, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return articleSearchDao.findByTitleOrContentLike(keyWords, keyWords, pageRequest);
    }
}
