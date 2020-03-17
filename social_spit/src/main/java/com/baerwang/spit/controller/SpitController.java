package com.baerwang.spit.controller;

import com.baerwang.spit.pojo.Spit;
import com.baerwang.spit.service.SpitService;
import com.baerwang.common.entity.PageResult;
import com.baerwang.common.entity.Result;
import com.baerwang.common.entity.Constant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 控制层
 *
 * @author baerwang
 * @version 1.0
 * @date 2020/2/18 19:12
 */
@CrossOrigin
@RestController
@RequestMapping("/spit")
public class SpitController {

    @Autowired
    private SpitService spitService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private HttpServletRequest httpServletRequest;

    /**
     * 查询全部数据
     *
     * @return
     */
    @GetMapping
    public Result findAll() {
        return new Result(true, Constant.OK, Constant.SELECT_SUCCESS, spitService.findAll());
    }


    /**
     * 根据Id查询
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result findOne(@PathVariable String id) {
        return new Result(true, Constant.OK, Constant.SELECT_SUCCESS, spitService.findById(id));
    }

    /**
     * 根据上级id查询吐槽列表
     *
     * @param parentId
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/comment/{parentId}/{page}/{size}")
    public Result findByParentId(@PathVariable String parentId, @PathVariable int page, @PathVariable int size) {
        Page<Spit> pageList = spitService.findByParentId(parentId, page, size);
        return new Result(true, Constant.OK, Constant.SELECT_SUCCESS, new PageResult<Spit>(pageList.getTotalElements(), pageList.getContent()));
    }


    /**
     * 添加
     *
     * @param spit
     * @return
     */
    @PostMapping
    public Result add(@RequestBody Spit spit) {
        String token = (String) httpServletRequest.getAttribute("user_claims");
        if (StringUtils.isEmpty(token)) {
            return new Result(false, Constant.ACCESS_ERROR, Constant.PERMISSION_ERROR);
        }
        spitService.add(spit);
        return new Result(true, Constant.OK, Constant.ADD_SUCCESS);
    }

    /**
     * 修改
     *
     * @param spit
     * @param id
     * @return
     */
    @PutMapping("/{id}")
    public Result update(@RequestBody Spit spit, @PathVariable String id) {
        spit.set_id(id);
        spitService.update(spit);
        return new Result(true, Constant.OK, Constant.UPDATE_SUCCESS);
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable String id) {
        spitService.deleteById(id);
        return new Result(true, Constant.OK, Constant.DELETE_SUCCESS);
    }

    /**
     * 点赞
     *
     * @param id
     * @return
     */
    @PutMapping("/thumbup/{id}")
    public Result updateThumbup(@PathVariable String id) {
        /*判断用户是否点过赞*/
        String userId = "666";
        if (redisTemplate.opsForValue().get("thumbup_" + userId + "_" + id) != null) {
            return new Result(false, Constant.REP_ERROR, Constant.LIKE_ALREADY);
        }
        spitService.updateThumbup(id);
        redisTemplate.opsForValue().set("thumbup_" + userId + "_" + id, "1");
        return new Result(true, Constant.OK, Constant.LIKE_SUCCESS);
    }

}
