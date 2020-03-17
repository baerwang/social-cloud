package com.baerwang.spit.dao;

import com.baerwang.spit.pojo.Spit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * 数据访问接口
 *
 * @author baerwang
 * @version 1.0
 * @date 2020/2/18 19:07
 */
public interface SpitDao extends MongoRepository<Spit, String> {
    /**
     * 根据上级ID查询吐槽列表(分页)
     *
     * @param parentId
     * @param pageable
     * @return
     */
    Page<Spit> findByParentId(String parentId, Pageable pageable);
}
