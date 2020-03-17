package com.baerwang.spit.service;

import com.baerwang.spit.dao.SpitDao;
import com.baerwang.spit.pojo.Spit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baerwang.common.utils.IdWorker;
import com.baerwang.common.utils.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * 服务层
 *
 * @author baerwang
 * @version 1.0
 * @date 2020/2/18 19:08
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SpitService {
    @Autowired
    private SpitDao spitDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 查询全部记录
     *
     * @return
     */
    public List<Spit> findAll() {
        return spitDao.findAll();
    }

    /**
     * 根据主键查询实体
     *
     * @param id
     * @return
     */
    public Spit findById(String id) {
        return spitDao.findById(id).orElse(null);
    }

    /**
     * 发布吐槽或吐槽评论
     *
     * @param spit
     */
    public void add(Spit spit) {
        /*主键*/
        spit.set_id(idWorker.nextId() + "");
        /*发布日期*/
        spit.setPublishtime(new Date());
        /*浏览量*/
        spit.setVisits(0);
        /*分享数*/
        spit.setShare(0);
        /*点赞数*/
        spit.setThumbup(0);
        /*回复数*/
        spit.setComment(0);
        /*状态*/
        spit.setState("1");
        /*如果当前添加吐槽,有父节点,那么父节点的吐槽回复数+1*/
        if (!StringUtils.isNullOrEmpty(spit.getParentId())) {
            /*如果存在上级id，评论*/
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(spit.getParentId()));
            Update update = new Update();
            update.inc("comment", 1);
            mongoTemplate.updateFirst(query, update, "spit");
        }
        spitDao.save(spit);
    }

    /**
     * 修改
     *
     * @param spit
     */
    public void update(Spit spit) {
        spitDao.save(spit);
    }

    /**
     * 删除已
     *
     * @param id
     */
    public void deleteById(String id) {
        spitDao.deleteById(id);
    }

    /**
     * 根据上级id查询吐槽列表
     *
     * @param parentId
     * @param page
     * @param size
     * @return
     */
    public Page<Spit> findByParentId(String parentId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return spitDao.findByParentId(parentId, pageRequest);
    }

    /**
     * 点赞
     *
     * @param id
     */
    public void updateThumbup(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        Update update = new Update();
        update.inc("thumbup", 1);
        mongoTemplate.updateFirst(query, update, "spit");
    }


}
