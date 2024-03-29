package com.baerwang.qa.service;

import com.baerwang.qa.dao.ProblemDao;
import com.baerwang.qa.pojo.Problem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
public class ProblemService {

    @Autowired
    private ProblemDao problemDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 最新回答
     *
     * @param labelId
     * @param currentPage
     * @param pageSize
     * @return
     */
    public Page<Problem> newList(String labelId, int currentPage, int pageSize) {
        PageRequest pageRequest = PageRequest.of(currentPage - 1, pageSize);
        return problemDao.newList(labelId, pageRequest);
    }

    /**
     * 热门回答
     *
     * @param labelId
     * @param currentPage
     * @param pageSize
     * @return
     */
    public Page<Problem> hotList(String labelId, int currentPage, int pageSize) {
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
        return problemDao.hotList(labelId, pageable);
    }

    /**
     * 等待回答
     *
     * @param labelId
     * @param currentPage
     * @param pageSize
     * @return
     */
    public Page<Problem> waitList(String labelId, int currentPage, int pageSize) {
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
        return problemDao.waitList(labelId, pageable);
    }

    /**
     * 查询全部列表
     *
     * @return
     */
    public List<Problem> findAll() {
        return problemDao.findAll();
    }


    /**
     * 条件查询+分页
     *
     * @param whereMap
     * @param page
     * @param size
     * @return
     */
    public Page<Problem> findSearch(Map whereMap, int page, int size) {
        Specification<Problem> specification = createSpecification(whereMap);
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return problemDao.findAll(specification, pageRequest);
    }


    /**
     * 条件查询
     *
     * @param whereMap
     * @return
     */
    public List<Problem> findSearch(Map whereMap) {
        Specification<Problem> specification = createSpecification(whereMap);
        return problemDao.findAll(specification);
    }

    /**
     * 根据ID查询实体
     *
     * @param id
     * @return
     */
    public Problem findById(String id) {
        return problemDao.findById(id).get();
    }

    /**
     * 增加
     *
     * @param problem
     */
    public void add(Problem problem) {
        problem.setId(idWorker.nextId() + "");
        problemDao.save(problem);
    }

    /**
     * 修改
     *
     * @param problem
     */
    public void update(Problem problem) {
        problemDao.save(problem);
    }

    /**
     * 删除
     *
     * @param id
     */
    public void deleteById(String id) {
        problemDao.deleteById(id);
    }

    /**
     * 动态条件构建
     *
     * @param searchMap
     * @return
     */
    private Specification<Problem> createSpecification(Map searchMap) {
        return new Specification<Problem>() {
            @Override
            public Predicate toPredicate(Root<Problem> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                /*ID*/
                if (!StringUtils.isNullOrEmpty(searchMap.get("id"))) {
                    predicateList.add(criteriaBuilder.like(root.get("id").as(String.class), "%" + (String) searchMap.get("id") + "%"));
                }
                /*标题*/
                if (!StringUtils.isNullOrEmpty(searchMap.get("title"))) {
                    predicateList.add(criteriaBuilder.like(root.get("title").as(String.class), "%" + (String) searchMap.get("title") + "%"));
                }
                /*内容*/
                if (!StringUtils.isNullOrEmpty(searchMap.get("content"))) {
                    predicateList.add(criteriaBuilder.like(root.get("content").as(String.class), "%" + (String) searchMap.get("content") + "%"));
                }
                /*用户ID*/
                if (!StringUtils.isNullOrEmpty(searchMap.get("userid"))) {
                    predicateList.add(criteriaBuilder.like(root.get("userid").as(String.class), "%" + (String) searchMap.get("userid") + "%"));
                }
                /*昵称*/
                if (!StringUtils.isNullOrEmpty(searchMap.get("nickname"))) {
                    predicateList.add(criteriaBuilder.like(root.get("nickname").as(String.class), "%" + (String) searchMap.get("nickname") + "%"));
                }
                /*是否解决*/
                if (!StringUtils.isNullOrEmpty(searchMap.get("solve"))) {
                    predicateList.add(criteriaBuilder.like(root.get("solve").as(String.class), "%" + (String) searchMap.get("solve") + "%"));
                }
                /*回复人昵称*/
                if (!StringUtils.isNullOrEmpty(searchMap.get("replyname"))) {
                    predicateList.add(criteriaBuilder.like(root.get("replyname").as(String.class), "%" + (String) searchMap.get("replyname") + "%"));
                }
                return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
            }
        };

    }

}
