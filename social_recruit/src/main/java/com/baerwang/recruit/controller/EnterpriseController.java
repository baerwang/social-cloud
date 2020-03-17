package com.baerwang.recruit.controller;

import com.baerwang.recruit.pojo.Enterprise;
import com.baerwang.recruit.service.EnterpriseService;
import com.baerwang.common.entity.PageResult;
import com.baerwang.common.entity.Result;
import com.baerwang.common.entity.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 控制器层
 *
 * @author baerwang
 * @version 1.0
 * @date 2020/2/16 12:30
 */
@RestController
@RequestMapping("/enterprise")
public class EnterpriseController {

    @Autowired
    private EnterpriseService enterpriseService;


    @GetMapping("/search/hotlist")
    public Result hotList() {
        List<Enterprise> enterprises = enterpriseService.hotList("1");
        return new Result(true, Constant.OK, Constant.SELECT_SUCCESS, enterprises);
    }

    @GetMapping
    public Result findAll() {
        return new Result(true, Constant.OK, Constant.SELECT_SUCCESS, enterpriseService.findAll());
    }

    @GetMapping("/{enterpriseId}")
    public Result findById(@PathVariable("enterpriseId") String id) {
        return new Result(true, Constant.OK, Constant.SELECT_SUCCESS, enterpriseService.findById(id));
    }

    @PutMapping("/{enterpriseId}")
    public Result update(@PathVariable("enterpriseId") String id, @RequestBody Enterprise enterprise) {
        enterprise.setId(id);
        enterpriseService.update(enterprise);
        return new Result(true, Constant.OK, Constant.UPDATE_SUCCESS);
    }

    @DeleteMapping("/{enterpriseId}")
    public Result deleteById(@PathVariable("enterpriseId") String id) {
        enterpriseService.deleteById(id);
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
        Page<Enterprise> pageList = enterpriseService.findSearch(searchMap, page, size);
        return new Result(true, Constant.OK, Constant.SELECT_SUCCESS, new PageResult<Enterprise>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 根据条件查询
     *
     * @param searchMap
     * @return
     */
    @PostMapping("/search")
    public Result findSearch(@RequestBody Map searchMap) {
        return new Result(true, Constant.OK, Constant.SELECT_SUCCESS, enterpriseService.findSearch(searchMap));
    }

    /**
     * 增加
     *
     * @param enterprise
     */
    @PostMapping
    public Result add(@RequestBody Enterprise enterprise) {
        enterpriseService.add(enterprise);
        return new Result(true, Constant.OK, Constant.ADD_SUCCESS);
    }

    /**
     * 修改
     *
     * @param enterprise
     */
    @PutMapping("/{id}")
    public Result update(@RequestBody Enterprise enterprise, @PathVariable String id) {
        enterprise.setId(id);
        enterpriseService.update(enterprise);
        return new Result(true, Constant.OK, Constant.UPDATE_SUCCESS);
    }

    /**
     * 删除
     *
     * @param id
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable String id) {
        enterpriseService.deleteById(id);
        return new Result(true, Constant.OK, Constant.DELETE_SUCCESS);
    }

}
