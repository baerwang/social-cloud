package com.baerwang.friend.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 用户客户端
 *
 * @author baerwang
 * @version 1.0
 * @date 2020/2/24 11:28
 */
@Component
@FeignClient("social-user")
public interface UserClient {

    /**
     * 更新好友粉丝数和用户关注数
     *
     * @param userId
     * @param friendId
     * @param fansCount
     */
    @PostMapping("/user/incFans/{userId}/{friendId}/{fansCount}")
    void updateFansCountAndFollowCount(@PathVariable String userId, @PathVariable String friendId, @PathVariable int fansCount);

}
