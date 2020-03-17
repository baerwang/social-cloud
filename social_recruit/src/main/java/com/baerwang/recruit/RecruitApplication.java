package com.baerwang.recruit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import com.baerwang.common.utils.IdWorker;

/**
 * 启动类
 *
 * @author baerwang
 * @version 1.0
 * @date 2020/2/15 9:45
 */
@EnableDiscoveryClient
@SpringBootApplication
public class RecruitApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecruitApplication.class, args);
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
