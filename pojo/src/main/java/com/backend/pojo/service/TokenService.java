package com.backend.pojo.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.backend.pojo.exception.TokenExpiredException;
import com.backend.pojo.pojo.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenService {

    private String secretKey = "kljskbfdabjfhgdshgfuewy6rt63gwuygfwyegsufgesu";
    public String getToken(String username, Integer type) {
        Date start = new Date();
        long currentTime = System.currentTimeMillis() + 3 * 24 * 60 * 60 * 1000;//三天有效时间
        Date end = new Date(currentTime);
        String token = "";

        token = JWT.create().withAudience(username).withIssuedAt(start).withExpiresAt(end)
                .withClaim("username", username)
                .withClaim("type", String.valueOf(type))
                .sign(Algorithm.HMAC256(secretKey));
        return token;
    }
    public static String getUserNameByToken(String token) throws TokenExpiredException {
//        String token = request.getHeader("token");
        DecodedJWT jwt = JWT.decode(token);
        if (isTokenExpired(jwt)) {
            throw new TokenExpiredException("Token已过期");
        }
        return jwt.getClaim("username")
                .asString();
    }

    public static Integer getUserTypeByToken(String token) throws TokenExpiredException {
//        String token = request.getHeader("token");
        DecodedJWT jwt = JWT.decode(token);
        if (isTokenExpired(jwt)) {
            throw new TokenExpiredException("Token已过期");
        }
        return Integer.valueOf(jwt.getClaim("type")
                .asString()) ;
    }

    private static boolean isTokenExpired(DecodedJWT jwt) {
        Date expiresAt = jwt.getExpiresAt();
        return expiresAt != null && expiresAt.before(new Date());
    }
}
