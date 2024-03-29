package com.baerwang.user.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 实体类
 *
 * @author baerwang
 * @version 1.0
 * @date 2020/2/21 12:18
 */
@Entity
@Table(name = "tb_admin")
public class Admin implements Serializable {

    private static final long serialVersionUID = 3044075416689064609L;

    @Id
    private String id;
    /**
     * 登陆名称
     */
    private String loginname;
    /**
     * 密码
     */
    private String password;
    /**
     * 状态
     */
    private String state;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
