package com.baerwang.article.service;

import com.baerwang.article.dao.ArticleDao;
import com.baerwang.article.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.baerwang.common.utils.IdWorker;
import com.baerwang.common.utils.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 服务层
 *
 * @author baerwang
 * @version 1.0
 * @date 2020/2/18 12:00
 */
@Service
@Transactional(rollbackOn = Exception.class)
public class ArticleService {

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 文章审核
     *
     * @param id
     */
    public void updateState(String id) {
        articleDao.updateState(id);
    }

    /**
     * 点赞
     *
     * @param id
     */
    public void addThumbup(String id) {
        articleDao.addThumbup(id);
    }

    /**
     * 查询全部列表
     *
     * @return
     */
    public List<Article> findAll() {
        return articleDao.findAll();
    }

    /**
     * 根据ID查询实体
     *
     * @param id
     * @return
     */
    public Article findById(String id) {
        /*先从缓存中查询当前对象*/
        Article article = (Article) redisTemplate.opsForValue().get("article_" + id);
        /*如果没有取到*/
        if (null == article) {
            /*从数据库中查询*/
            article = articleDao.findById(id).orElse(null);
            /*存入缓存,设置一天过期时间*/
            redisTemplate.opsForValue().set("article_" + id, article, 1, TimeUnit.DAYS);
        }
        return article;
    }

    /**
     * 条件查询+分页
     *
     * @param whereMap
     * @param page
     * @param size
     * @return
     */
    public Page<Article> findSearch(Map whereMap, int page, int size) {
        Specification<Article> specification = createSpecification(whereMap);
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return articleDao.findAll(specification, pageRequest);
    }


    /**
     * 条件查询
     *
     * @param whereMap
     * @return
     */
    public List<Article> findSearch(Map whereMap) {
        Specification<Article> specification = createSpecification(whereMap);
        return articleDao.findAll(specification);
    }

    /**
     * 增加
     *
     * @param article
     */
    public void add(Article article) {
        article.setId(idWorker.nextId() + "");
        articleDao.save(article);
    }

    /**
     * 修改
     *
     * @param article
     */
    public void update(Article article) {
        /*删除缓存*/
        redisTemplate.delete("article_" + article.getId());
        articleDao.save(article);
    }

    /**
     * 删除
     *
     * @param id
     */
    public void deleteById(String id) {
        /*删除缓存*/
        redisTemplate.delete("article_" + id);
        articleDao.deleteById(id);
    }

    /**
     * 动态条件构建
     *
     * @param searchMap
     * @return
     */
    private Specification<Article> createSpecification(Map searchMap) {
        return new Specification<Article>() {
            @Override
            public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                /*ID*/
                if (!StringUtils.isNullOrEmpty(searchMap.get("id"))) {
                    predicateList.add(criteriaBuilder.like(root.get("id").as(String.class), "%" + (String) searchMap.get("id") + "%"));
                }
                /*专栏ID*/
                if (!StringUtils.isNullOrEmpty(searchMap.get("columnid"))) {
                    predicateList.add(criteriaBuilder.like(root.get("columnid").as(String.class), "%" + (String) searchMap.get("columnid") + "%"));
                }
                /*用户ID*/
                if (!StringUtils.isNullOrEmpty(searchMap.get("userid"))) {
                    predicateList.add(criteriaBuilder.like(root.get("userid").as(String.class), "%" + (String) searchMap.get("userid") + "%"));
                }
                /*标题*/
                if (!StringUtils.isNullOrEmpty(searchMap.get("title"))) {
                    predicateList.add(criteriaBuilder.like(root.get("title").as(String.class), "%" + (String) searchMap.get("title") + "%"));
                }
                /*文章正文*/
                if (!StringUtils.isNullOrEmpty(searchMap.get("content"))) {
                    predicateList.add(criteriaBuilder.like(root.get("content").as(String.class), "%" + (String) searchMap.get("content") + "%"));
                }
                /*文章封面*/
                if (!StringUtils.isNullOrEmpty(searchMap.get("image"))) {
                    predicateList.add(criteriaBuilder.like(root.get("image").as(String.class), "%" + (String) searchMap.get("image") + "%"));
                }
                /*是否公开*/
                if (!StringUtils.isNullOrEmpty(searchMap.get("ispublic"))) {
                    predicateList.add(criteriaBuilder.like(root.get("ispublic").as(String.class), "%" + (String) searchMap.get("ispublic") + "%"));
                }
                /*是否置顶*/
                if (!StringUtils.isNullOrEmpty(searchMap.get("istop"))) {
                    predicateList.add(criteriaBuilder.like(root.get("istop").as(String.class), "%" + (String) searchMap.get("istop") + "%"));
                }
                /*审核状态*/
                if (!StringUtils.isNullOrEmpty(searchMap.get("state"))) {
                    predicateList.add(criteriaBuilder.like(root.get("state").as(String.class), "%" + (String) searchMap.get("state") + "%"));
                }
                /*所属频道*/
                if (!StringUtils.isNullOrEmpty(searchMap.get("channelid"))) {
                    predicateList.add(criteriaBuilder.like(root.get("channelid").as(String.class), "%" + (String) searchMap.get("channelid") + "%"));
                }
                /*URL*/
                if (!StringUtils.isNullOrEmpty(searchMap.get("url"))) {
                    predicateList.add(criteriaBuilder.like(root.get("url").as(String.class), "%" + (String) searchMap.get("url") + "%"));
                }
                /*类型*/
                if (!StringUtils.isNullOrEmpty(searchMap.get("type"))) {
                    predicateList.add(criteriaBuilder.like(root.get("type").as(String.class), "%" + (String) searchMap.get("type") + "%"));
                }
                return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
            }
        };

    }

}
