package com.baerwang.search.controller;

import com.baerwang.search.pojo.Article;
import com.baerwang.search.service.ArticleSearchService;
import com.baerwang.common.entity.PageResult;
import com.baerwang.common.entity.Result;
import com.baerwang.common.entity.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * 控制器层
 *
 * @author baerwang
 * @version 1.0
 * @date 2020/2/19 21:22
 */
@CrossOrigin
@RestController
@RequestMapping("/article")
public class ArticleSearchController {
    @Autowired
    private ArticleSearchService articleSearchService;

    /**
     * 增加文章
     *
     * @param article
     * @return
     */
    @PostMapping
    public Result save(@RequestBody Article article) {
        articleSearchService.add(article);
        return new Result(true, Constant.OK, Constant.ADD_SUCCESS);
    }

    /**
     * 检索+分页
     *
     * @param keyWords
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/search/{keyWords}/{page}/{size}")
    public Result findByTitleLike(@PathVariable String keyWords, @PathVariable int page, @PathVariable int size) {
        Page<Article> articlePage = articleSearchService.findByTitleLike(keyWords, page, size);
        return new Result(true, Constant.OK, Constant.SELECT_SUCCESS, new PageResult<Article>(articlePage.getTotalElements(), articlePage.getContent()));
    }
}
