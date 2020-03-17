package com.baerwang.base.service;

import com.baerwang.base.dao.LabelDao;
import com.baerwang.base.pojo.Label;
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
 * 标签(TbLabel)表服务实现类
 *
 * @author baerwang
 * @version 1.0
 * @date 2020-02-14 10:59:29
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class LabelService {
    @Autowired
    private LabelDao labelDao;

    @Autowired
    private IdWorker idWorker;

    public List<Label> findAll() {
        return labelDao.findAll();
    }

    public Label findById(String id) {
        return labelDao.findById(id).orElse(null);
    }

    public void save(Label label) {
        label.setId((idWorker.nextId() + ""));
        labelDao.save(label);
    }

    public void update(Label label) {
        labelDao.save(label);
    }

    public void deleteById(String id) {
        labelDao.deleteById(id);
    }

    public List<Label> findSearch(Map map) {
        Specification specification = createSpecification(map);
        return labelDao.findAll(specification);
    }

    public Page<Label> findSearch(Map searchMap, int page, int size) {
        Specification<Label> specification = createSpecification(searchMap);
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return labelDao.findAll(specification, pageRequest);
    }

    private Specification<Label> createSpecification(Map map) {
        return new Specification<Label>() {
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<>();
                if (!StringUtils.isNullOrEmpty(map.get("labelname"))) {
                    predicateList.add(criteriaBuilder.like(root.get("labelname").as(String.class), "%" + (String) map.get("lablename" + "%")));
                }
                if (!StringUtils.isNullOrEmpty(map.get("state"))) {
                    predicateList.add(criteriaBuilder.equal(root.get("state").as(String.class), (String) map.get("state")));
                }
                if (!StringUtils.isNullOrEmpty(map.get("recommend"))) {
                    predicateList.add(criteriaBuilder.equal(root.get("recommend").as(String.class), (String) map.get("recommend")));
                }
                return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
            }
        };
    }
}