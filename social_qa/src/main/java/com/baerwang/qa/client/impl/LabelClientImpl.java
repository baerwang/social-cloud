package com.baerwang.qa.client.impl;

import com.baerwang.qa.client.LabelClient;
import com.baerwang.common.entity.Constant;
import com.baerwang.common.entity.Result;
import org.springframework.stereotype.Component;

/**
 * 实现接口类
 *
 * @author baerwang
 * @version 1.0
 * @date 2020/2/24 12:25
 */
@Component
public class LabelClientImpl implements LabelClient {
    @Override
    public Result findById(String id) {
        return new Result(false, Constant.ERROR, "熔断器启动了");
    }
}
