package com.baerwang.common.entity;

/**
 * 返回码定义类
 *
 * @author baerwang
 * @version 1.0
 * @date 2020/2/14 9:25
 */
public interface Constant {
    /**
     * 成功
     */
    int OK = 20000;
    /**
     * 失败
     */
    int ERROR = 20001;
    /**
     * 用户名或密码错误
     */
    int LOGIN_ERROR = 20002;
    /**
     * 权限不足
     */
    int ACCESS_ERROR = 20003;
    /**
     * 远程调用失败
     */
    int REMOTE_ERROR = 20004;
    /**
     * 重复操作
     */
    int REP_ERROR = 20005;

    String SELECT_SUCCESS = "查询成功";

    String SELECT_ERROR = "查询失败";

    String UPDATE_SUCCESS = "修改成功";

    String UPDATE_ERROR = "修改失败";

    String ADD_SUCCESS = "增加成功";

    String ADD_ERROR = "增加失败";

    String DELETE_SUCCESS = "删除成功";

    String DELETE_ERROR = "删除失败";

    String COMMIT_SUCCESS = "提交成功";

    String COMMIT_ERROR = "提交失败";

    String LIKE_SUCCESS = "点赞成功";

    String LIKE_ALREADY = "你已经点过赞了";

    String SEND_SUCCESS = "发送成功";

    String SEND_ERROR = "发送失败";

    String REGISTER_SUCCESS = "注册成功";

    String REGISTER_ERROR = "注册失败";

    String PERMISSION_ERROR = "权限不足";

}
