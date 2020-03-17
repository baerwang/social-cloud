package com.baerwang.user.service;

import com.baerwang.user.dao.UserDao;
import com.baerwang.user.pojo.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baerwang.common.utils.IdWorker;
import com.baerwang.common.utils.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 接口层
 *
 * @author baerwang
 * @version 1.0
 * @date 2020/2/21 12:07
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private HttpServletRequest httpServletRequest;

    private final String PREFIX_SMS_CODE = "smsCode_";

    /**
     * 根据手机号和密码查询用户
     *
     * @param mobile
     * @param password
     * @return
     */
    public User findByMobileAndPassword(String mobile, String password) {
        User user = userDao.findByMobile(mobile);
        if (user != null && bCryptPasswordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    /**
     * 发送短信验证码
     *
     * @param mobile
     */
    public void sendSms(String mobile) {
        /*1.生成六位短信验证码*/
        String random = RandomStringUtils.randomNumeric(6);
        System.out.println("收到的验证码是：" + random);
        /*缓存放一份，将验证码放入redis*/
        redisTemplate.opsForValue().set(PREFIX_SMS_CODE + mobile, random + "", 5, TimeUnit.MINUTES);
        /*将验证码和手机号发送到rabbitmq*/
        Map<String, String> map = new HashMap<>(1);
        map.put("mobile", mobile);
        map.put("code", random + "");
        rabbitTemplate.convertAndSend("sms", map);
    }

    /**
     * 增加
     *
     * @param user
     * @param code
     */
    public void add(User user, String code) {
        user.setId(idWorker.nextId() + "");
        String newPassword = bCryptPasswordEncoder.encode(user.getPassword());
        /*密码加密*/
        user.setPassword(newPassword);
        /*关注数*/
        user.setFollowcount(0);
        /*粉丝数*/
        user.setFanscount(0);
        /*在线时长*/
        user.setRegdate(new Date());
        /*注册日期*/
        user.setOnline(0L);
        /*更新日期*/
        user.setUpdatedate(new Date());
        /*最后登陆日期*/
        user.setLastdate(new Date());
        userDao.save(user);
    }

    /**
     * 查询全部列表
     *
     * @return
     */
    public List<User> findAll() {
        return userDao.findAll();
    }


    /**
     * 条件查询+分页
     *
     * @param whereMap
     * @param page
     * @param size
     * @return
     */
    public Page<User> findSearch(Map whereMap, int page, int size) {
        Specification<User> specification = createSpecification(whereMap);
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return userDao.findAll(specification, pageRequest);
    }


    /**
     * 条件查询
     *
     * @param whereMap
     * @return
     */
    public List<User> findSearch(Map whereMap) {
        Specification<User> specification = createSpecification(whereMap);
        return userDao.findAll(specification);
    }

    /**
     * 根据ID查询实体
     *
     * @param id
     * @return
     */
    public User findById(String id) {
        return userDao.findById(id).orElse(null);
    }


    /**
     * 修改
     *
     * @param user
     */
    public void update(User user) {
        userDao.save(user);
    }

    /**
     * 删除
     *
     * @param id
     */
    public void deleteById(String id) {
        userDao.deleteById(id);
    }

    /**
     * 更新好友粉丝数和用户关注数
     *
     * @param fansCount
     * @param userId
     */
    public void updateFansCountAndFollowCount(int fansCount, String userId, String friendId) {
        userDao.updateFansCount(fansCount, friendId);
        userDao.updateFollowCount(fansCount, userId);
    }


    /**
     * 动态条件构建
     *
     * @param searchMap
     * @return
     */
    private Specification<User> createSpecification(Map searchMap) {
        return new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                /*ID*/
                if (StringUtils.isNullOrEmpty(searchMap.get("id"))) {
                    predicateList.add(criteriaBuilder.like(root.get("id").as(String.class), "%" + (String) searchMap.get("id") + "%"));
                }
                /*手机号码*/
                if (StringUtils.isNullOrEmpty(searchMap.get("mobile"))) {
                    predicateList.add(criteriaBuilder.like(root.get("mobile").as(String.class), "%" + (String) searchMap.get("mobile") + "%"));
                }
                /*密码*/
                if (StringUtils.isNullOrEmpty(searchMap.get("password"))) {
                    predicateList.add(criteriaBuilder.like(root.get("password").as(String.class), "%" + (String) searchMap.get("password") + "%"));
                }
                /*昵称*/
                if (StringUtils.isNullOrEmpty(searchMap.get("nickname"))) {
                    predicateList.add(criteriaBuilder.like(root.get("nickname").as(String.class), "%" + (String) searchMap.get("nickname") + "%"));
                }
                /*性别*/
                if (StringUtils.isNullOrEmpty(searchMap.get("sex"))) {
                    predicateList.add(criteriaBuilder.like(root.get("sex").as(String.class), "%" + (String) searchMap.get("sex") + "%"));
                }
                /*头像*/
                if (StringUtils.isNullOrEmpty(searchMap.get("avatar"))) {
                    predicateList.add(criteriaBuilder.like(root.get("avatar").as(String.class), "%" + (String) searchMap.get("avatar") + "%"));
                }
                /*E-Mail*/
                if (StringUtils.isNullOrEmpty(searchMap.get("email"))) {
                    predicateList.add(criteriaBuilder.like(root.get("email").as(String.class), "%" + (String) searchMap.get("email") + "%"));
                }
                /*兴趣*/
                if (StringUtils.isNullOrEmpty(searchMap.get("interest"))) {
                    predicateList.add(criteriaBuilder.like(root.get("interest").as(String.class), "%" + (String) searchMap.get("interest") + "%"));
                }
                /*个性*/
                if (StringUtils.isNullOrEmpty(searchMap.get("personality"))) {
                    predicateList.add(criteriaBuilder.like(root.get("personality").as(String.class), "%" + (String) searchMap.get("personality") + "%"));
                }
                return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
            }
        };
    }


}
