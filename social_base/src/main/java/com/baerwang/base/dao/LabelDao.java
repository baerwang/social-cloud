package com.baerwang.base.dao;

import com.baerwang.base.pojo.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 标签(TbLabel)表数据库访问层
 *
 * @author baerwang
 * @version 1.0
 * @date 2020-02-14 10:59:29
 */
public interface LabelDao extends JpaRepository<Label, String>, JpaSpecificationExecutor<Label> {
}