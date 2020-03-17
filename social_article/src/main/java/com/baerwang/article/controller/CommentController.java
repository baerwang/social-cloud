package com.baerwang.article.controller;

import com.baerwang.article.pojo.Comment;
import com.baerwang.article.service.CommentService;
import com.baerwang.common.entity.Result;
import com.baerwang.common.entity.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 控制器层
 *
 * @author baerwang
 * @version 1.0
 * @date 2020/2/18 21:45
 */
@CrossOrigin
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 添加评论
     *
     * @param comment
     * @return
     */
    @PostMapping
    public Result save(@RequestBody Comment comment) {
        commentService.add(comment);
        return new Result(true, Constant.OK, Constant.COMMIT_SUCCESS);
    }

    /**
     * 根据文章Id查询评论列表
     *
     * @param articleId
     * @return
     */
    @GetMapping("/article/{articleId}")
    public Result findByArticledId(@PathVariable String articleId) {
        return new Result(true, Constant.OK, Constant.SELECT_SUCCESS, commentService.findByArticleId(articleId));
    }

    /**
     * 删除评论
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable String id) {
        commentService.deleteById(id);
        return new Result(true, Constant.OK, Constant.DELETE_SUCCESS);
    }


}
