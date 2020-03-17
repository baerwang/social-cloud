package com.baerwang.gathering.service;

import com.baerwang.gathering.dao.GatheringDao;
import com.baerwang.gathering.pojo.Gathering;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
 * @author baerwang
 * @version 1.0
 * @date 2020/2/18 12:46
 */
@Service
@Cacheable(value = "gathering")
@Transactional(rollbackFor = Exception.class)
public class GatheringService {

    @Autowired
    private GatheringDao gatheringDao;

    @Autowired
    private IdWorker idWorker;


    /**
     * 根据id查询实体
     *
     * @param id
     * @return
     */
    @Cacheable(key = "#id")
    public Gathering findById(String id) {
        return gatheringDao.findById(id).orElse(null);
    }

    /**
     * 修改
     *
     * @param gathering
     */
    @CacheEvict(key = "#gathering.id")
    public void update(Gathering gathering) {
        gatheringDao.save(gathering);
    }

    /**
     * 删除
     *
     * @param id
     */
    @CacheEvict(key = "#id")
    public void deleteById(String id) {
        gatheringDao.deleteById(id);
    }

    /**
     * 增加
     *
     * @param gathering
     */
    public void add(Gathering gathering) {
        gathering.setId(idWorker.nextId() + "");
        gatheringDao.save(gathering);
    }

    /**
     * 查询全部列表
     *
     * @return
     */
    public List<Gathering> findAll() {
        return gatheringDao.findAll();
    }

    /**
     * 条件查询+分页
     *
     * @param whereMap
     * @param page
     * @param size
     * @return
     */
    public Page<Gathering> findSearch(Map whereMap, int page, int size) {
        Specification<Gathering> specification = createSpecification(whereMap);
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return gatheringDao.findAll(specification, pageRequest);
    }


    /**
     * 条件查询
     *
     * @param whereMap
     * @return
     */
    public List<Gathering> findSearch(Map whereMap) {
        Specification<Gathering> specification = createSpecification(whereMap);
        return gatheringDao.findAll(specification);
    }

    /**
     * 动态条件构建
     *
     * @param searchMap
     * @return
     */
    private Specification<Gathering> createSpecification(Map searchMap) {
        return new Specification<Gathering>() {
            @Override
            public Predicate toPredicate(Root<Gathering> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
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
