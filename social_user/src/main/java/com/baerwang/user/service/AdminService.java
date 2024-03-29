package com.baerwang.user.service;

import com.baerwang.user.dao.AdminDao;
import com.baerwang.user.pojo.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
 * @author Administrator
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AdminService {

    @Autowired
    private AdminDao adminDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 根据登陆名和密码查询
     *
     * @param loginname
     * @param password
     * @return
     */
    public Admin findByLoginnameAndPassword(String loginname, String password) {
        Admin admin = adminDao.findByLoginname(loginname);
        if (admin != null && bCryptPasswordEncoder.matches(password, admin.getPassword())) {
            return admin;
        }
        return null;
    }

    /**
     * 查询全部列表
     *
     * @return
     */
    public List<Admin> findAll() {
        return adminDao.findAll();
    }

    /**
     * 条件查询+分页
     *
     * @param whereMap
     * @param page
     * @param size
     * @return
     */
    public Page<Admin> findSearch(Map whereMap, int page, int size) {
        Specification<Admin> specification = createSpecification(whereMap);
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return adminDao.findAll(specification, pageRequest);
    }


    /**
     * 条件查询
     *
     * @param whereMap
     * @return
     */
    public List<Admin> findSearch(Map whereMap) {
        Specification<Admin> specification = createSpecification(whereMap);
        return adminDao.findAll(specification);
    }

    /**
     * 根据ID查询实体
     *
     * @param id
     * @return
     */
    public Admin findById(String id) {
        return adminDao.findById(id).orElse(null);
    }

    /**
     * 添加
     *
     * @param admin
     */
    public void add(Admin admin) {
        admin.setId(idWorker.nextId() + "");
        /*密码加密*/
        String password = bCryptPasswordEncoder.encode(admin.getPassword());
        /*加密后的密码*/
        admin.setPassword(password);
        adminDao.save(admin);
    }

    /**
     * 修改
     *
     * @param admin
     */
    public void update(Admin admin) {
        adminDao.save(admin);
    }

    /**
     * 删除
     *
     * @param id
     */
    public void deleteById(String id) {
        adminDao.deleteById(id);
    }

    /**
     * 动态条件构建
     *
     * @param searchMap
     * @return
     */
    private Specification<Admin> createSpecification(Map searchMap) {
        return new Specification<Admin>() {
            @Override
            public Predicate toPredicate(Root<Admin> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                /*ID*/
                if (!StringUtils.isNullOrEmpty(searchMap.get("id"))) {
                    predicateList.add(cb.like(root.get("id").as(String.class), "%" + (String) searchMap.get("id") + "%"));
                }
                /*登陆名称*/
                if (!StringUtils.isNullOrEmpty(searchMap.get("loginname"))) {
                    predicateList.add(cb.like(root.get("loginname").as(String.class), "%" + (String) searchMap.get("loginname") + "%"));
                }
                /*密码*/
                if (!StringUtils.isNullOrEmpty(searchMap.get("password"))) {
                    predicateList.add(cb.like(root.get("password").as(String.class), "%" + (String) searchMap.get("password") + "%"));
                }
                /*状态*/
                if (!StringUtils.isNullOrEmpty(searchMap.get("state"))) {
                    predicateList.add(cb.like(root.get("state").as(String.class), "%" + (String) searchMap.get("state") + "%"));
                }
                return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
            }
        };

    }
}
