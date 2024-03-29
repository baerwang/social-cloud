package com.baerwang.qa.service;

import com.baerwang.qa.dao.ReplyDao;
import com.baerwang.qa.pojo.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baerwang.common.utils.IdWorker;
import com.baerwang.common.utils.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 服务层
 *
 * @author baerwang
 * @version 1.0
 * @date 2020/2/18 11:28
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ReplyService {

    @Autowired
    private ReplyDao replyDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 查询全部列表
     *
     * @return
     */
    public List<Reply> findAll() {
        return replyDao.findAll();
    }


    /**
     * 条件查询+分页
     *
     * @param whereMap
     * @param page
     * @param size
     * @return
     */
    public Page<Reply> findSearch(Map whereMap, int page, int size) {
        Specification<Reply> specification = createSpecification(whereMap);
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return replyDao.findAll(specification, pageRequest);
    }


    /**
     * 条件查询
     *
     * @param whereMap
     * @return
     */
    public List<Reply> findSearch(Map whereMap) {
        Specification<Reply> specification = createSpecification(whereMap);
        return replyDao.findAll(specification);
    }

    /**
     * 根据ID查询实体
     *
     * @param id
     * @return
     */
    public Reply findById(String id) {
        return replyDao.findById(id).get();
    }

    /**
     * 增加
     *
     * @param reply
     */
    public void add(Reply reply) {
        reply.setId(idWorker.nextId() + "");
        replyDao.save(reply);
    }

    /**
     * 修改
     *
     * @param reply
     */
    public void update(Reply reply) {
        replyDao.save(reply);
    }

    /**
     * 删除
     *
     * @param id
     */
    public void deleteById(String id) {
        replyDao.deleteById(id);
    }

    /**
     * 动态条件构建
     *
     * @param searchMap
     * @return
     */
    private Specification<Reply> createSpecification(Map searchMap) {
        return new Specification<Reply>() {
            @Override
            public Predicate toPredicate(Root<Reply> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                /*编号*/
                if (!StringUtils.isNullOrEmpty(searchMap.get("id"))) {
                    predicateList.add(criteriaBuilder.like(root.get("id").as(String.class), "%" + (String) searchMap.get("id") + "%"));
                }
                /*问题ID*/
                if (!StringUtils.isNullOrEmpty(searchMap.get("problemid"))) {
                    predicateList.add(criteriaBuilder.like(root.get("problemid").as(String.class), "%" + (String) searchMap.get("problemid") + "%"));
                }
                /*回答内容*/
                if (!StringUtils.isNullOrEmpty(searchMap.get("content"))) {
                    predicateList.add(criteriaBuilder.like(root.get("content").as(String.class), "%" + (String) searchMap.get("content") + "%"));
                }
                /*回答人ID*/
                if (!StringUtils.isNullOrEmpty(searchMap.get("userid"))) {
                    predicateList.add(criteriaBuilder.like(root.get("userid").as(String.class), "%" + (String) searchMap.get("userid") + "%"));
                }
                /*回答人昵称*/
                if (!StringUtils.isNullOrEmpty(searchMap.get("nickname"))) {
                    predicateList.add(criteriaBuilder.like(root.get("nickname").as(String.class), "%" + (String) searchMap.get("nickname") + "%"));
                }
                return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
            }
        };
    }
}
