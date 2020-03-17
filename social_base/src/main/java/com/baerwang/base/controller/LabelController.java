package com.baerwang.base.controller;

import com.baerwang.base.pojo.Label;
import com.baerwang.base.service.LabelService;
import com.baerwang.common.entity.PageResult;
import com.baerwang.common.entity.Result;
import com.baerwang.common.entity.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 标签(TbLabel)表控制层
 *
 * @author baerwang
 * @version 1.0
 * @date 2020-02-14 10:59:29
 */
@CrossOrigin
@RefreshScope
@RestController
@RequestMapping("/label")
public class LabelController {
    /**
     * 服务对象
     */
    @Autowired
    private LabelService labelService;

    /**
     * 查询全部
     *
     * @return
     */
    @GetMapping
    public Result findAll() {
        return new Result(true, Constant.OK, Constant.SELECT_SUCCESS, labelService.findAll());
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result findById(@PathVariable String id) {
        return new Result(true, Constant.OK, Constant.SELECT_SUCCESS, labelService.findById(id));
    }

    /**
     * 添加
     *
     * @param label
     * @return
     */
    @PostMapping("/save")
    public Result save(@RequestBody Label label) {
        labelService.save(label);
        return new Result(true, Constant.OK, Constant.ADD_SUCCESS);
    }

    /**
     * 修改标签
     *
     * @param label
     * @param id
     * @return
     */
    @PutMapping("/updateById/{id}")
    public Result update(@RequestBody Label label, @PathVariable String id) {
        label.setId(id);
        labelService.update(label);
        return new Result(true, Constant.OK, Constant.UPDATE_SUCCESS);
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable String id) {
        labelService.deleteById(id);
        return new Result(true, Constant.OK, Constant.DELETE_SUCCESS);
    }

    /**
     * 条件查询
     *
     * @param searchMap
     * @return
     */
    @PostMapping("/search")
    public Result<List> findSearch(@RequestBody Map searchMap) {
        return new Result<>(true, Constant.OK, Constant.SELECT_SUCCESS, labelService.findSearch(searchMap));
    }

    /**
     * 条件+分页查询
     *
     * @param searchMap
     * @param page
     * @param size
     * @return
     */
    @PostMapping("/search/{page}/{size}")
    public Result<List> findSearch(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int size) {
        Page pageList = labelService.findSearch(searchMap, page, size);
        return new Result(true, Constant.OK, Constant.SELECT_SUCCESS, new PageResult<>(pageList.getTotalElements(), pageList.getContent()));
    }

}