package com.baerwang.friend.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * (TbNofriend)实体类
 *
 * @author baerwang
 * @version 1.0
 * @date 2020/2/23 21:09
 */
@Entity
@Table(name = "tb_nofriend")
@IdClass(NoFriend.class)    // 表示是联合主键
public class NoFriend implements Serializable {
    private static final long serialVersionUID = 784018688846917893L;
    /**
     * 用户ID
     */
    @Id
    private String userId;
    /**
     * 好友ID
     */
    private String friendId;


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
}