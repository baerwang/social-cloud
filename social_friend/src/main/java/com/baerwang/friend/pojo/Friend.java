package com.baerwang.friend.pojo;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * (TbFriend)实体类
 *
 * @author baerwang
 * @version 1.0
 * @date 2020/2/23 21:09
 */
@Entity
@Table(name = "tb_friend")
@IdClass(Friend.class)  // 表示是联合主键
public class Friend implements Serializable {
    private static final long serialVersionUID = 134351707582422610L;
    /**
     * 用户ID
     */
    @Id
    private String userId;
    /**
     * 好友ID
     */
    @Id
    private String friendId;
    /**
     * 是否互相喜欢
     */
    private String isLike;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }

    public String getIsLike() {
        return isLike;
    }

    public void setIsLike(String isLike) {
        this.isLike = isLike;
    }
}