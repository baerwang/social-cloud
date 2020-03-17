package com.baerwang.article.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.baerwang.article.pojo.Channel;
import com.baerwang.article.service.ChannelService;

import com.baerwang.common.entity.PageResult;
import com.baerwang.common.entity.Result;
import com.baerwang.common.entity.Constant;

/**
 * 控制器层
 *
 * @author baerwang
 * @version 1.0
 * @date 2020/2/18 12:00
 */
@CrossOrigin
@RestController
@RequestMapping("/channel")
public class ChannelController {

    @Autowired
    private ChannelService channelService;


    /**
     * 查询全部数据
     *
     * @return
     */
    @GetMapping
    public Result findAll() {
        return new Result(true, Constant.OK, Constant.SELECT_SUCCESS, channelService.findAll());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return
     */
    @GetMapping("/{id}")
    public Result findById(@PathVariable String id) {
        return new Result(true, Constant.OK, Constant.SELECT_SUCCESS, channelService.findById(id));
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
        Page<Channel> pageList = channelService.findSearch(searchMap, page, size);
        return new Result(true, Constant.OK, Constant.SELECT_SUCCESS, new PageResult<Channel>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 根据条件查询
     *
     * @param searchMap
     * @return
     */
    @PostMapping("/search")
    public Result findSearch(@RequestBody Map searchMap) {
        return new Result(true, Constant.OK, Constant.SELECT_SUCCESS, channelService.findSearch(searchMap));
    }

    /**
     * 增加
     *
     * @param channel
     */
    @PostMapping
    public Result add(@RequestBody Channel channel) {
        channelService.add(channel);
        return new Result(true, Constant.OK, Constant.ADD_SUCCESS);
    }

    /**
     * 修改
     *
     * @param channel
     */
    @PutMapping("/{id}")
    public Result update(@RequestBody Channel channel, @PathVariable String id) {
        channel.setId(id);
        channelService.update(channel);
        return new Result(true, Constant.OK, Constant.UPDATE_SUCCESS);
    }

    /**
     * 删除
     *
     * @param id
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable String id) {
        channelService.deleteById(id);
        return new Result(true, Constant.OK, Constant.DELETE_SUCCESS);
    }

}
