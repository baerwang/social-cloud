package com.baerwang.recruit.controller;

import com.baerwang.recruit.pojo.Recruit;
import com.baerwang.recruit.service.RecruitService;
import com.baerwang.common.entity.PageResult;
import com.baerwang.common.entity.Result;
import com.baerwang.common.entity.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 控制器层
 *
 * @author baerwang
 * @version 1.0
 * @date 2020/2/16 12:30
 */
@RestController
@RequestMapping("/recruit")
public class RecruitController {

    @Autowired
    private RecruitService recruitService;

    /**
     * 推荐职位
     *
     * @return
     */
    @GetMapping("/search/recommand")
    public Result recommend() {
        return new Result(true, Constant.OK, Constant.SELECT_SUCCESS, recruitService.recommend());
    }

    /**
     * 最新职位
     *
     * @return
     */
    @GetMapping("/search/newlist")
    public Result newList() {
        return new Result(true, Constant.OK, Constant.SELECT_SUCCESS, recruitService.newList());
    }

    @GetMapping
    public Result findAll() {
        return new Result(true, Constant.OK, Constant.SELECT_SUCCESS, recruitService.findAll());
    }

    @GetMapping("/{recruitId}")
    public Result findById(@PathVariable("recruitId") String id) {
        return new Result(true, Constant.OK, Constant.SELECT_SUCCESS, recruitService.findById(id));
    }

    @PostMapping
    public Result save(@RequestBody Recruit recruit) {
        recruitService.save(recruit);
        return new Result(true, Constant.OK, Constant.ADD_SUCCESS);
    }

    @PutMapping("/{recruitId}")
    public Result update(@PathVariable("recruitId") String id, @RequestBody Recruit recruit) {
        recruit.setId(id);
        recruitService.update(recruit);
        return new Result(true, Constant.OK, Constant.UPDATE_SUCCESS);
    }

    @DeleteMapping("/{recruitId}")
    public Result deleteById(@PathVariable("recruitId") String id) {
        recruitService.deleteById(id);
        return new Result(true, Constant.OK, Constant.DELETE_SUCCESS);
    }

    /**
     * 分页+多条件查询
     *
     * @param searchMap 查询条件封装
     * @param page      页码
     * @param size      页大小
     * @return 分页结果
     */
    @PostMapping("/search/{page}/{size}")
    public Result findSearch(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int size) {
        Page<Recruit> pageList = recruitService.findSearch(searchMap, page, size);
        return new Result(true, Constant.OK, Constant.SELECT_SUCCESS, new PageResult<>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 根据条件查询
     *
     * @param searchMap
     * @return
     */
    @PostMapping("/search")
    public Result findSearch(@RequestBody Map searchMap) {
        return new Result(true, Constant.OK, Constant.SELECT_SUCCESS, recruitService.findSearch(searchMap));
    }

    /**
     * 修改
     *
     * @param recruit
     */
    @PutMapping( "/{id}")
    public Result update(@RequestBody Recruit recruit, @PathVariable String id) {
        recruit.setId(id);
        recruitService.update(recruit);
        return new Result(true, Constant.OK, Constant.UPDATE_SUCCESS);
    }

    /**
     * 删除
     *
     * @param id
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable String id) {
        recruitService.deleteById(id);
        return new Result(true, Constant.OK, Constant.DELETE_SUCCESS);
    }
}
