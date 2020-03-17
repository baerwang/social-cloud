package com.baerwang.gathering.controller;

import com.baerwang.gathering.pojo.Gathering;
import com.baerwang.gathering.service.GatheringService;
import com.baerwang.common.entity.PageResult;
import com.baerwang.common.entity.Result;
import com.baerwang.common.entity.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * * 控制器层
 *
 * @author baerwang
 * @version 1.0
 * @date 2020/2/18 12:46
 */
@RequestMapping("/gathering")
@RestController
public class GatheringController {

    @Autowired
    private GatheringService gatheringService;

    /**
     * 删除
     *
     * @param id
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable String id) {
        gatheringService.deleteById(id);
        return new Result(true, Constant.OK, Constant.DELETE_SUCCESS);
    }

    /**
     * 根据条件查询
     *
     * @param searchMap
     * @return
     */
    @PostMapping("/search")
    public Result search(@RequestBody Map searchMap) {
        return new Result(true, Constant.OK, Constant.SELECT_SUCCESS, gatheringService.findSearch(searchMap));
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
    public Result search(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int size) {
        Page<Gathering> pageList = gatheringService.findSearch(searchMap, page, size);
        return new Result(true, Constant.OK, Constant.SELECT_SUCCESS, new PageResult<Gathering>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 增加
     *
     * @param gathering
     */
    @PostMapping
    public Result add(@RequestBody Gathering gathering) {
        gatheringService.add(gathering);
        return new Result(true, Constant.OK, Constant.ADD_SUCCESS);
    }

    /**
     * 查询全部数据
     *
     * @return
     */
    @GetMapping
    public Result findAll() {
        return new Result(true, Constant.OK, Constant.SELECT_SUCCESS, gatheringService.findAll());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return
     */
    @GetMapping("/{id}")
    public Result findById(@PathVariable String id) {
        return new Result(true, Constant.OK, Constant.SELECT_SUCCESS, gatheringService.findById(id));
    }

    /**
     * 修改
     *
     * @param gathering
     */
    @PutMapping("/{id}")
    public Result update(@RequestBody Gathering gathering, @PathVariable String id) {
        gathering.setId(id);
        gatheringService.update(gathering);
        return new Result(true, Constant.OK, Constant.UPDATE_SUCCESS);
    }


}
