package com.baerwang.user.controller;

import com.baerwang.user.pojo.User;
import com.baerwang.user.service.UserService;
import com.baerwang.common.entity.PageResult;
import com.baerwang.common.entity.Result;
import com.baerwang.common.entity.Constant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import com.baerwang.common.utils.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 控制层
 *
 * @author baerwang
 * @version 1.0
 * @date 2020/2/21 12:18
 */
@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private HttpServletRequest httpServletRequest;

    private final String PREFIX_SMS_CODE = "smsCode_";

    /**
     * 用户登录
     *
     * @param user
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        User userLogin = userService.findByMobileAndPassword(user.getMobile(), user.getPassword());
        if (userLogin != null) {
            String token = jwtUtil.createJWT(userLogin.getId(), userLogin.getMobile(), "user");
            Map<String, Object> map = new HashMap<>(2);
            map.put("token", token);
            /*昵称*/
            map.put("name", userLogin.getNickname());
            /*头像*/
            map.put("avatar", userLogin.getAvatar());
            return new Result(true, Constant.OK, "登陆成功", map);
        }
        return new Result(false, Constant.LOGIN_ERROR, "用户名或密码错误");
    }

    /**
     * 用户注册
     *
     * @param user
     * @param code
     * @return
     */
    @PostMapping("/register/{code}")
    public Result register(@RequestBody User user, @PathVariable String code) {
        /*判断验证码是否正确*/
        String mobile = (String) redisTemplate.opsForValue().get(PREFIX_SMS_CODE + user.getMobile());
        if (mobile == null) {
            throw new RuntimeException("请点击获取短信验证码");
        }
        /*提取系统正确的验证码*/
        if (!mobile.equals(code)) {
            throw new RuntimeException("验证码输入不正确");
        }
        userService.add(user, code);
        return new Result(true, Constant.OK, Constant.REGISTER_SUCCESS);
    }

    /**
     * 发送验证码
     *
     * @param mobile
     * @return
     */
    @PostMapping("/sendSms/{mobile}")
    public Result sendSms(@PathVariable String mobile) {
        userService.sendSms(mobile);
        return new Result(true, Constant.OK, Constant.SEND_SUCCESS);
    }

    /**
     * 查询全部数据
     *
     * @return
     */
    @GetMapping
    public Result findAll() {
        return new Result(true, Constant.OK, Constant.SELECT_SUCCESS, userService.findAll());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return
     */
    @GetMapping("/{id}")
    public Result findById(@PathVariable String id) {
        return new Result(true, Constant.OK, Constant.SELECT_SUCCESS, userService.findById(id));
    }


    /**
     * 分页+多条件查询
     *
     * @param searchMap 查询条件封装
     * @param page      页码
     * @param size      页大小
     * @return 分页结果
     */
    @PostMapping("/search/{page}/{size}")
    public Result findSearch(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int size) {
        Page<User> pageList = userService.findSearch(searchMap, page, size);
        return new Result(true, Constant.OK, Constant.SELECT_SUCCESS, new PageResult<User>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 根据条件查询
     *
     * @param searchMap
     * @return
     */
    @PostMapping("/search")
    public Result findSearch(@RequestBody Map searchMap) {
        return new Result(true, Constant.OK, Constant.SELECT_SUCCESS, userService.findSearch(searchMap));
    }

    /**
     * 修改
     *
     * @param user
     */
    @PutMapping("/{id}")
    public Result update(@RequestBody User user, @PathVariable String id) {
        user.setId(id);
        userService.update(user);
        return new Result(true, Constant.OK, Constant.UPDATE_SUCCESS);
    }

    /**
     * 增加
     *
     * @param user
     */
    @PostMapping
    public Result add(@RequestBody User user) {
        userService.add(user, null);
        return new Result(true, Constant.OK, Constant.ADD_SUCCESS);
    }

    /**
     * 删除
     *
     * @param id
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable String id) {
        String token = (String) httpServletRequest.getAttribute("admin_claims");
        if (StringUtils.isEmpty(token)) {
            return new Result(false, Constant.ACCESS_ERROR, Constant.PERMISSION_ERROR);
        }
        userService.deleteById(id);
        return new Result(true, Constant.OK, Constant.DELETE_SUCCESS);
    }

    /**
     * 更新好友粉丝数和用户关注数
     *
     * @param userId
     * @param friendId
     * @return
     */
    @PostMapping("/incFans/{userId}/{friendId}/{fansCount}")
    public Result updateFansCountAndFollowCount(@PathVariable String userId, @PathVariable String friendId, @PathVariable int fansCount) {
        userService.updateFansCountAndFollowCount(fansCount, userId, friendId);
        return new Result(true, Constant.OK, Constant.ADD_SUCCESS);
    }

}
