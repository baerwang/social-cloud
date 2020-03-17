package com.baerwang.recruit.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.baerwang.recruit.pojo.Enterprise;

import java.util.List;

/**
 * 数据访问接口
 *
 * @author baerwang
 * @version 1.0
 * @date 2020/2/16 12:30
 * *
 */
public interface EnterpriseDao extends JpaRepository<Enterprise, String>, JpaSpecificationExecutor<Enterprise> {
    /**
     * 根据热门状态获取企业列表
     *
     * @param ishot
     * @return
     */
    List<Enterprise> findByIshot(String ishot);
}
