package com.baerwang.gathering;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import com.baerwang.common.utils.IdWorker;

/**
 * 启动类
 *
 * @author baerwang
 * @version 1.0
 * @date 2020/2/18 12:43
 */
@EnableCaching
@SpringBootApplication
public class GatheringApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatheringApplication.class, args);
    }

    /**
     * 将id生成器放到容器中
     *
     * @return
     */
    @Bean
    public IdWorker idWorker() {
        return new IdWorker(1, 1);
    }
}
