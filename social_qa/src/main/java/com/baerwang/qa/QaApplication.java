package com.baerwang.qa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import com.baerwang.common.utils.IdWorker;
import com.baerwang.common.utils.JwtUtil;

/**
 * 启动类
 *
 * @author baerwang
 * @version 1.0
 * @date 2020/2/16 9:45
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class QaApplication {
    public static void main(String[] args) {
        SpringApplication.run(QaApplication.class, args);
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

    @Bean
    public JwtUtil jwtUtil() {
        return new JwtUtil();
    }
}
