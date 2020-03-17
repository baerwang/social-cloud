package com.baerwang.article.dao;

import com.baerwang.article.pojo.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * 数据访问接口
 *
 * @author baerwang
 * @version 1.0
 * @date 2020/2/18 21:41
 */
public interface CommentDao extends MongoRepository<Comment, String> {
    /**
     * 根据文章Id查询评论列表
     *
     * @param articleId
     * @return
     */
    List<Comment> findByArticleId(String articleId);
}
