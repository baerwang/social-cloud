package com.baerwang.friend.service;

import com.baerwang.friend.dao.FriendDao;
import com.baerwang.friend.dao.NoFriendDao;
import com.baerwang.friend.pojo.Friend;
import com.baerwang.friend.pojo.NoFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 服务层
 *
 * @author baerwang
 * @version 1.0
 * @date 2020/2/23 22:38
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class FriendService {

    @Autowired
    private FriendDao friendDao;

    @Autowired
    private NoFriendDao noFriendDao;

    /**
     * 添加好友
     *
     * @param userId
     * @param friendId
     * @return
     */
    public int addFriend(String userId, String friendId) {
        /*判断userId到friendId是否有数据,有就是重复添加好友*/
        Friend friend = friendDao.findByUserIdAndFriendId(userId, friendId);
        if (friend != null) {
            return 0;
        }
        /*添加好友，让好友表中userId到friendId的方向的type为0*/
        friend = new Friend();
        friend.setUserId(userId);
        friend.setFriendId(friendId);
        friend.setIsLike("0");
        friendDao.save(friend);
        /*判断从friendId到userId是否有数据,如果有,把双方的状态都改为1*/
        if (friendDao.findByUserIdAndFriendId(friendId, userId) != null) {
            /*把双方的isLike都改成1*/
            friendDao.updateIsLike("1", userId, friendId);
            friendDao.updateIsLike("1", friendId, userId);
        }
        return 1;
    }

    /**
     * 向不喜欢列表中添加记录
     *
     * @param userId
     * @param friendId
     */
    public void addNoFriend(String userId, String friendId) {
        NoFriend noFriend = new NoFriend();
        noFriend.setUserId(userId);
        noFriend.setFriendId(friendId);
        noFriendDao.save(noFriend);
    }

    /**
     * 删除好友
     *
     * @param userId
     * @param friendId
     */
    public void deleteFriend(String userId, String friendId) {
        /*删除表中userId和friendId这条数据*/
        friendDao.deleteFriend(userId, friendId);
        /*更新friendId到userId的isLike为0*/
        friendDao.updateIsLike("0", friendId, userId);
        /*向不喜欢表中添加记录*/
        addNoFriend(userId, friendId);
    }
}
