package com.learn.douyin.common.utils;

import io.jsonwebtoken.*;
import org.springframework.util.StringUtils;

import java.util.Date;

public class TokenUtil {

    //过期时间
    private static long tokenExpiration = 7*24*60*60*1000;
    //签名秘钥
    private static String tokenSignKey = "13132141216651356";

    //根据参数生成token
    public static String createToken(Long userId, String username) {
            String token = Jwts.builder()
                .setSubject("DOUYIN-USER")
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                .claim("userId", userId)
                .claim("username", username)
                .signWith(SignatureAlgorithm.HS512, tokenSignKey)
                .compressWith(CompressionCodecs.GZIP)
                .compact();
        return token;
    }

    //检验token,正确返回true
    public static boolean verify(String token){
        try {
            Jwts.parser().setSigningKey(tokenSignKey).parse(token);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    //根据token字符串得到用户id
    public static Long getUserId(String token) {
        if(StringUtils.isEmpty(token)) return null;

        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        Integer userId = (Integer)claims.get("userId");
        return userId.longValue();
    }

    //根据token字符串得到用户名称
    public static String getUsername(String token) {
        if(StringUtils.isEmpty(token)) return "";

        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        return (String)claims.get("username");
    }

}

