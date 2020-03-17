package com.baerwang.article.service;

import com.baerwang.article.dao.CommentDao;
import com.baerwang.article.pojo.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baerwang.common.utils.IdWorker;

import java.util.List;

/**
 * 服务层
 *
 * @author baerwang
 * @version 1.0
 * @date 2020/2/18 21:42
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CommentService {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 添加评论
     *
     * @param comment
     */
    public void add(Comment comment) {
        comment.set_id(idWorker.nextId() + "");
        commentDao.save(comment);
    }

    /**
     * 根据文章Id查询评论列表
     *
     * @param articleId
     * @return
     */
    public List<Comment> findByArticleId(String articleId) {
        return commentDao.findByArticleId(articleId);
    }

    /**
     * 删除评论
     *
     * @param id
     */
    public void deleteById(String id) {
        commentDao.deleteById(id);
    }
}
