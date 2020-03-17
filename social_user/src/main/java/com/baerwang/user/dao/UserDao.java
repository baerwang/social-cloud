package com.baerwang.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.baerwang.user.pojo.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 *
 * @author baerwang
 * @version 1.0
 * @date 2020/2/21 12:18
 */
public interface UserDao extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {
    /**
     * 根据手机号查询用户
     *
     * @param mobile
     * @return
     */
    User findByMobile(String mobile);

    /**
     * 更新粉丝数
     *
     * @param fansCount
     * @param friendId
     */
    @Modifying
    @Query(value = "UPDATE tb_user SET fanscount = fanscount + ? WHERE id = ?", nativeQuery = true)
    void updateFansCount(int fansCount, String friendId);

    /**
     * 更新关注数
     *
     * @param fansCount
     * @param userId
     */
    @Modifying
    @Query(value = "UPDATE tb_user SET followcount = followcount + ? WHERE id = ?", nativeQuery = true)
    void updateFollowCount(int fansCount, String userId);
}
