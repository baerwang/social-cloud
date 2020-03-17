package com.baerwang.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.baerwang.user.pojo.Admin;

/**
 * 数据访问接口
 *
 * @author baerwang
 * @version 1.0
 * @date 2020/2/21 12:18
 */
public interface AdminDao extends JpaRepository<Admin, String>, JpaSpecificationExecutor<Admin> {
    /**
     * 根据登陆名和密码查询
     *
     * @param loginname
     * @return
     */
    Admin findByLoginname(String loginname);
}
