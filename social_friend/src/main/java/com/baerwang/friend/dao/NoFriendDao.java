package com.baerwang.friend.dao;

import com.baerwang.friend.pojo.NoFriend;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 不喜欢列表数据访问层
 *
 * @author baerwang
 * @version 1.0
 * @date 2020/2/24 10:33
 */
public interface NoFriendDao extends JpaRepository<NoFriend, String> {
}
