package com.baerwang.qa.controller;

import com.baerwang.qa.client.LabelClient;
import com.baerwang.qa.pojo.Problem;
import com.baerwang.qa.service.ProblemService;
import com.baerwang.common.entity.PageResult;
import com.baerwang.common.entity.Result;
import com.baerwang.common.entity.Constant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 控制器层
 *
 * @author baerwang
 * @version 1.0
 * @date 2020/2/18 11:28
 */
@RestController
@CrossOrigin
@RequestMapping("/problem")
public class ProblemController {

    @Autowired
    private ProblemService problemService;

    @Autowired
    private LabelClient labelClient;

    @Autowired
    private HttpServletRequest httpServletRequest;


    /**
     * 根据标签ID查询最新问题列表
     *
     * @param labelId
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GetMapping("/newlist/{label}/{page}/{size}")
    public Result newList(@PathVariable("label") String labelId, @PathVariable("page") int currentPage,
                          @PathVariable("size") int pageSize) {
        Page<Problem> pageData = problemService.newList(labelId, currentPage, pageSize);
        return new Result(true, Constant.OK, Constant.SELECT_SUCCESS,
                new PageResult<>(pageData.getTotalElements(), pageData.getContent()));
    }

    /**
     * 根据标签ID查询热门问题列表
     *
     * @param labelId
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GetMapping("/hotlist/{label}/{page}/{size}")
    public Result hotList(@PathVariable("label") String labelId, @PathVariable("page") int currentPage,
                          @PathVariable("size") int pageSize) {
        Page<Problem> pageData = problemService.hotList(labelId, currentPage, pageSize);
        return new Result(true, Constant.OK, Constant.SELECT_SUCCESS,
                new PageResult<>(pageData.getTotalElements(), pageData.getContent()));
    }

    /**
     * 根据标签ID查询等待回答列表
     *
     * @param labelId
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GetMapping("/waitlist/{label}/{page}/{size}")
    public Result waitList(@PathVariable("label") String labelId, @PathVariable("page") int currentPage,
                           @PathVariable("size") int pageSize) {
        Page<Problem> pageData = problemService.waitList(labelId, currentPage, pageSize);
        return new Result(true, Constant.OK, Constant.SELECT_SUCCESS,
                new PageResult<>(pageData.getTotalElements(), pageData.getContent()));
    }

    /**
     * 根据id查询标签
     *
     * @param labelId
     * @return
     */
    @GetMapping("/label/{labelId}")
    public Result findLabelById(@PathVariable String labelId) {
        return labelClient.findById(labelId);
    }

    /**
     * 查询全部数据
     *
     * @return
     */
    @GetMapping
    public Result findAll() {
        return new Result(true, Constant.OK, Constant.SELECT_SUCCESS, problemService.findAll());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return
     */
    @GetMapping("/{id}")
    public Result findById(@PathVariable String id) {
        return new Result(true, Constant.OK, Constant.SELECT_SUCCESS, problemService.findById(id));
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
        Page<Problem> pageList = problemService.findSearch(searchMap, page, size);
        return new Result(true, Constant.OK, Constant.SELECT_SUCCESS, new PageResult<Problem>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 根据条件查询
     *
     * @param searchMap
     * @return
     */
    @PostMapping("/search")
    public Result findSearch(@RequestBody Map searchMap) {
        return new Result(true, Constant.OK, Constant.SELECT_SUCCESS, problemService.findSearch(searchMap));
    }

    /**
     * 增加
     *
     * @param problem
     */
    @PostMapping
    public Result add(@RequestBody Problem problem) {
        String token = (String) httpServletRequest.getAttribute("user_claims");
        if (StringUtils.isEmpty(token)) {
            return new Result(false, Constant.ACCESS_ERROR, Constant.PERMISSION_ERROR);
        }
        problemService.add(problem);
        return new Result(true, Constant.OK, Constant.ADD_SUCCESS);
    }

    /**
     * 修改
     *
     * @param problem
     */
    @PutMapping("/{id}")
    public Result update(@RequestBody Problem problem, @PathVariable String id) {
        problem.setId(id);
        problemService.update(problem);
        return new Result(true, Constant.OK, Constant.UPDATE_SUCCESS);
    }

    /**
     * 删除
     *
     * @param id
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable String id) {
        problemService.deleteById(id);
        return new Result(true, Constant.OK, Constant.DELETE_SUCCESS);
    }

}
