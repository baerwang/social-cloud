package com.baerwang.article.controller;

import com.baerwang.article.pojo.Article;
import com.baerwang.article.service.ArticleService;
import com.baerwang.common.entity.PageResult;
import com.baerwang.common.entity.Result;
import com.baerwang.common.entity.Constant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 控制器层
 *
 * @author baerwang
 * @version 1.0
 * @date 2020/2/18 12:00
 */
@CrossOrigin
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    /**
     * 文章审核
     *
     * @param articleId
     * @return
     */
    @PutMapping("/examine/{articleId}")
    public Result examine(@PathVariable String articleId) {
        articleService.updateState(articleId);
        return new Result(true, Constant.OK, "审核成功");
    }

    /**
     * 文章点赞
     *
     * @param articleId
     * @return
     */
    @PutMapping("/thumbup/{articleId}")
    public Result thumbup(@PathVariable String articleId) {
        articleService.addThumbup(articleId);
        return new Result(true, Constant.OK, Constant.LIKE_SUCCESS);
    }

    /**
     * 按id查询
     *
     * @param articleId
     * @return
     */
    @GetMapping("/{articleId}")
    public Result findById(@PathVariable String articleId) {
        return new Result(true, Constant.OK, Constant.SELECT_SUCCESS, articleService.findById(articleId));
    }

    /**
     * 查询全部数据
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true, Constant.OK, Constant.SELECT_SUCCESS, articleService.findAll());
    }


    /**
     * 分页+多条件查询
     *
     * @param searchMap 查询条件封装
     * @param page      页码
     * @param size      页大小
     * @return 分页结果
     */
    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int size) {
        Page<Article> pageList = articleService.findSearch(searchMap, page, size);
        return new Result(true, Constant.OK, Constant.SELECT_SUCCESS, new PageResult<Article>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 根据条件查询
     *
     * @param searchMap
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap) {
        return new Result(true, Constant.OK, Constant.SELECT_SUCCESS, articleService.findSearch(searchMap));
    }

    /**
     * 增加
     *
     * @param article
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Article article) {
        String token = (String) httpServletRequest.getAttribute("user_claims");
        if (StringUtils.isEmpty(token)) {
            return new Result(false, Constant.ACCESS_ERROR, Constant.PERMISSION_ERROR);
        }
        articleService.add(article);
        return new Result(true, Constant.OK, Constant.ADD_SUCCESS);
    }

    /**
     * 修改
     *
     * @param article
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update(@RequestBody Article article, @PathVariable String id) {
        article.setId(id);
        articleService.update(article);
        return new Result(true, Constant.OK, Constant.UPDATE_SUCCESS);
    }

    /**
     * 删除
     *
     * @param id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id) {
        articleService.deleteById(id);
        return new Result(true, Constant.OK, Constant.DELETE_SUCCESS);
    }

}
