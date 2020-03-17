package com.baerwang.article.pojo;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * 实体类
 *
 * @author baerwang
 * @version 1.0
 * @date 2020/2/18 21:39
 */
public class Comment implements Serializable {
    private static final long serialVersionUID = 5871071399862334743L;

    @Id
    private String _id;
    /**
     * 文章id
     */
    private String articleId;
    /**
     * 内容
     */
    private String content;
    /**
     * 用户id
     */
    private String userid;
    /**
     * 上级id
     */
    private String parentid;
    /**
     * 发布日期
     */
    private Date publishdate;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public Date getPublishdate() {
        return publishdate;
    }

    public void setPublishdate(Date publishdate) {
        this.publishdate = publishdate;
    }
}
