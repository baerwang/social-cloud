package com.baerwang.jwt;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * @author baerwang
 * @version 1.0
 * @date 2020/2/22 16:37
 */
public class CreateJwtTest {
    public static void main(String[] args) {
        /*当前时间 */
        long now = System.currentTimeMillis();
        /*过期时间为1分钟*/
        long exp = now + 1000*60;
        JwtBuilder jwtBuilder = Jwts.builder()
                .setId("111")
                .setSubject("小熊")
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "baerwang")
                /*当前时间+过期时间为1分钟*/
                .setExpiration(new Date(exp));
        System.out.println(jwtBuilder.compact());
    }
}
