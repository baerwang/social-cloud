package com.baerwang.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author baerwang
 * @version 1.0
 * @date 2020/2/22 16:48
 */
public class ParseJwtTest {
    public static void main(String[] args) {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMjM0NTY3ODkxMiIsImlhdCI6MTU4MjQ3MjQ3OCwicm9sZXMiOiJ1c2VyIiwiZXhwIjoxNTgyNDcyODM4fQ.bLfd2P4H_n7AcOrBwuNHMuAWzijCVyS05M9O9AZTdyY";
        Claims claims = Jwts.parser().setSigningKey("social").parseClaimsJws(token).getBody();
        System.out.println(claims.getId());
        System.out.println("subject:" + claims.getSubject());
        System.out.println("roles:" + claims.get("roles"));
        System.out.println("logo:" + claims.get("logo"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy‐MM‐dd hh:mm:ss");
        System.out.println("签发时间:" + sdf.format(claims.getIssuedAt()));
        System.out.println("过期时间:" + sdf.format(claims.getExpiration()));
        System.out.println("当前时间:" + sdf.format(new Date()));
    }
}
