package com.baerwang.qa.client;

import com.baerwang.qa.client.impl.LabelClientImpl;
import com.baerwang.common.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 调用客户端
 *
 * @author baerwang
 * @version 1.0
 * @date 2020/2/23 15:19
 */
@Component
@FeignClient(name = "social-base", fallback = LabelClientImpl.class)
public interface LabelClient {
    /**
     * 根据id查询标签
     *
     * @param id
     * @return
     */
    @GetMapping("/label/{id}")
    Result findById(@PathVariable String id);
}
