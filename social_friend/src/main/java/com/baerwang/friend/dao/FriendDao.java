package com.baerwang.friend.dao;

import com.baerwang.friend.pojo.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 *
 * @author baerwang
 * @version 1.0
 * @date 2020/2/23 21:27
 */
public interface FriendDao extends JpaRepository<Friend, String> {
    /**
     * 按用户ID和朋友ID查找
     *
     * @param userId
     * @param friendId
     * @return
     */
    Friend findByUserIdAndFriendId(String userId, String friendId);

    /**
     * 更新互相喜欢
     *
     * @param isLike
     * @param userId
     * @param friendId
     */
    @Modifying
    @Query(value = "UPDATE tb_friend SET isLike = ? WHERE userId = ? AND friendId = ?", nativeQuery = true)
    void updateIsLike(String isLike, String userId, String friendId);

    /**
     * 删除喜欢
     *
     * @param userId
     * @param friendId
     */
    @Modifying
    @Query(value = "DELETE FROM tb_friend WHERE userId = ? and friendId = ?", nativeQuery = true)
    void deleteFriend(String userId, String friendId);
}
