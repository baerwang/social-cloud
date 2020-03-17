package com.baerwang.spit.pojo;

import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体类
 *
 * @author baerwang
 * @version 1.0
 * @date 2020/2/18 19:05
 */
public class Spit implements Serializable {

    private static final long serialVersionUID = -4393253896155900209L;

    @Id
    private String _id;
    /**
     * 内容
     */
    private String content;
    /**
     * 发布日期
     */
    private Date publishtime;
    /**
     * 用户id
     */
    private String userid;
    /**
     * 用户名称
     */
    private String nickname;
    /**
     * 浏览量
     */
    private Integer visits;
    /**
     * 分享数
     */
    private Integer share;
    /**
     * 点赞数
     */
    private Integer thumbup;
    /**
     * 回复数
     */
    private Integer comment;
    /**
     * 状态
     */
    private String state;
    /**
     * 上级id
     */
    private String parentId;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPublishtime() {
        return publishtime;
    }

    public void setPublishtime(Date publishtime) {
        this.publishtime = publishtime;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getVisits() {
        return visits;
    }

    public void setVisits(Integer visits) {
        this.visits = visits;
    }

    public Integer getThumbup() {
        return thumbup;
    }

    public void setThumbup(Integer thumbup) {
        this.thumbup = thumbup;
    }

    public Integer getShare() {
        return share;
    }

    public void setShare(Integer share) {
        this.share = share;
    }

    public Integer getComment() {
        return comment;
    }

    public void setComment(Integer comment) {
        this.comment = comment;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
