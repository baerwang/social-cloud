package com.baerwang.recruit.service;

import com.baerwang.recruit.dao.EnterpriseDao;
import com.baerwang.recruit.pojo.Enterprise;
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
 * @date 2020/2/16 12:30
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class EnterpriseService {

    @Autowired
    private EnterpriseDao enterpriseDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * @param ishot
     * @return
     */
    public List<Enterprise> hotList(String ishot) {
        return enterpriseDao.findByIshot(ishot);
    }

    /**
     * 查询全部列表
     *
     * @return
     */
    public List<Enterprise> findAll() {
        return enterpriseDao.findAll();
    }

    /**
     * 条件查询+分页
     *
     * @param whereMap
     * @param page
     * @param size
     * @return
     */
    public Page<Enterprise> findSearch(Map whereMap, int page, int size) {
        Specification<Enterprise> specification = createSpecification(whereMap);
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return enterpriseDao.findAll(specification, pageRequest);
    }

    /**
     * 条件查询
     *
     * @param whereMap
     * @return
     */
    public List<Enterprise> findSearch(Map whereMap) {
        Specification<Enterprise> specification = createSpecification(whereMap);
        return enterpriseDao.findAll(specification);
    }

    /**
     * 根据ID查询实体
     *
     * @param id
     * @return
     */
    public Enterprise findById(String id) {
        return enterpriseDao.findById(id).get();
    }

    /**
     * 增加
     *
     * @param enterprise
     */
    public void add(Enterprise enterprise) {
        enterprise.setId(idWorker.nextId() + "");
        enterpriseDao.save(enterprise);
    }

    /**
     * 修改
     *
     * @param enterprise
     */
    public void update(Enterprise enterprise) {
        enterpriseDao.save(enterprise);
    }

    /**
     * 删除
     *
     * @param id
     */
    public void deleteById(String id) {
        enterpriseDao.deleteById(id);
    }

    /**
     * 动态条件构建
     *
     * @param searchMap
     * @return
     */
    private Specification<Enterprise> createSpecification(Map searchMap) {
        return new Specification<Enterprise>() {
            @Override
            public Predicate toPredicate(Root<Enterprise> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                /*ID*/
                if (!StringUtils.isNullOrEmpty(searchMap.get("id"))) {
                    predicateList.add(cb.like(root.get("id").as(String.class), "%" + (String) searchMap.get("id") + "%"));
                }
                /*企业名称*/
                if (!StringUtils.isNullOrEmpty(searchMap.get("name"))) {
                    predicateList.add(cb.like(root.get("name").as(String.class), "%" + (String) searchMap.get("name") + "%"));
                }
                /*企业简介*/
                if (!StringUtils.isNullOrEmpty(searchMap.get("summary"))) {
                    predicateList.add(cb.like(root.get("summary").as(String.class), "%" + (String) searchMap.get("summary") + "%"));
                }
                /*企业地址*/
                if (!StringUtils.isNullOrEmpty(searchMap.get("address"))) {
                    predicateList.add(cb.like(root.get("address").as(String.class), "%" + (String) searchMap.get("address") + "%"));
                }
                /*标签列表*/
                if (!StringUtils.isNullOrEmpty(searchMap.get("labels"))) {
                    predicateList.add(cb.like(root.get("labels").as(String.class), "%" + (String) searchMap.get("labels") + "%"));
                }
                /*坐标*/
                if (!StringUtils.isNullOrEmpty(searchMap.get("coordinate"))) {
                    predicateList.add(cb.like(root.get("coordinate").as(String.class), "%" + (String) searchMap.get("coordinate") + "%"));
                }
                /*是否热门*/
                if (!StringUtils.isNullOrEmpty(searchMap.get("ishot"))) {
                    predicateList.add(cb.like(root.get("ishot").as(String.class), "%" + (String) searchMap.get("ishot") + "%"));
                }
                /*LOGO*/
                if (!StringUtils.isNullOrEmpty(searchMap.get("logo"))) {
                    predicateList.add(cb.like(root.get("logo").as(String.class), "%" + (String) searchMap.get("logo") + "%"));
                }
                /*URL*/
                if (!StringUtils.isNullOrEmpty(searchMap.get("url"))) {
                    predicateList.add(cb.like(root.get("url").as(String.class), "%" + (String) searchMap.get("url") + "%"));
                }
                return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
            }
        };
    }

}
