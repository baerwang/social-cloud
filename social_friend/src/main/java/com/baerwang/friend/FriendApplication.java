package com.baerwang.friend;

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
 * @date 2020/2/23 21:09
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class FriendApplication {
    public static void main(String[] args) {
        SpringApplication.run(FriendApplication.class, args);
    }

    @Bean
    public IdWorker idWorker() {
        return new IdWorker(1, 1);
    }

    @Bean
    public JwtUtil jwtUtil() {
        return new JwtUtil();
    }
}
