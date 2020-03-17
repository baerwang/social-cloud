package com.baerwang.friend.controller;

import com.baerwang.friend.client.UserClient;
import com.baerwang.friend.service.FriendService;
import com.baerwang.common.entity.Result;
import com.baerwang.common.entity.Constant;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 控制层
 *
 * @author baerwang
 * @version 1.0
 * @date 2020/2/23 21:42
 */
@RestController
@RequestMapping("/friend")
public class FriendController {

    @Autowired
    private FriendService friendService;

    @Autowired
    private UserClient userClient;

    @Autowired
    private HttpServletRequest httpServletRequest;

    /**
     * 添加好友
     *
     * @param friendId
     * @param type
     * @return
     */
    @PutMapping("/like/{friendId}/{type}")
    public Result addFriend(@PathVariable String friendId, @PathVariable String type) {
        /*验证是否登录,并且拿到当前登录的用户id*/
        Claims claims = (Claims) httpServletRequest.getAttribute("user_claims");
        if (null == claims) {
            return new Result(false, Constant.LOGIN_ERROR, Constant.PERMISSION_ERROR);
        }
        /*添加好友*/
        final String TYPE_BUDDY = "1";
        /*添加非好友*/
        final String TYPE_NO_BUDDY = "2";
        /*得到当前登录的用户id*/
        String userId = claims.getId();
        /*判断是否添加好友还是添加非好友*/
        if (type != null) {
            if (type.equals(TYPE_BUDDY)) {
                /*添加好友*/
                int flag = friendService.addFriend(userId, friendId);
                if (flag == 0) {
                    /*不能重复加好友*/
                    return new Result(false, Constant.ERROR, "不能重复加好友");
                } else if (flag == 1) {
                    /*添加成功*/
                    userClient.updateFansCountAndFollowCount(userId, friendId, 1);
                    return new Result(true, Constant.OK, Constant.ADD_SUCCESS);
                }
            } else if (type.equals(TYPE_NO_BUDDY)) {
                /*添加非好友*/
                friendService.addNoFriend(userId, friendId);
            }
        } else {
            return new Result(false, Constant.ERROR, "参数异常");
        }
        return new Result(true, Constant.OK, "操作成功");
    }

    /**
     * 删除好友
     *
     * @param friendId
     * @return
     */
    @DeleteMapping("/{friendId}")
    public Result deleteFriend(@PathVariable String friendId) {
        Claims claims = (Claims) httpServletRequest.getAttribute("user_claims");
        if (null == claims) {
            return new Result(false, Constant.LOGIN_ERROR, Constant.PERMISSION_ERROR);
        }
        String userId = claims.getId();
        friendService.deleteFriend(userId, friendId);
        userClient.updateFansCountAndFollowCount(userId, friendId, -1);
        return new Result(true, Constant.OK, Constant.DELETE_SUCCESS);
    }

}
