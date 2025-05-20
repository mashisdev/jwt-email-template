package com.mashisdev.jwtemail.services;

import com.mashisdev.jwtemail.model.UserEntity;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration-time}")
    private long JWT_EXPIRATION;

    @Value("${security.jwt.refresh-token.expiration-time}")
    private long JWT_REFRESH_EXPIRATION;

    // Claims related

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()));
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts
                    .parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

        } catch (ExpiredJwtException e) {
            return e.getClaims();
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            throw new RuntimeException("Invalid JWT token or mal formed", e);
        }
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean canTokenBeRefreshed(String token) {
        try {
            Claims claims = extractAllClaims(token);
            Date expiration = claims.getExpiration();
            long currentTime = System.currentTimeMillis();
            return expiration.before(new Date(currentTime)) &&
                    expiration.getTime() + JWT_REFRESH_EXPIRATION > currentTime;
        } catch (Exception e) {
            return false;
        }
    }

    public String refreshToken(String token, UserDetails userDetails) {
        if (!canTokenBeRefreshed(token)) {
            throw new IllegalArgumentException("The JWT couldn't be refreshed");
        }
        return generateToken(userDetails);
    }

    // ----------------------------------------------

//    public String extractUsername(String token) {
//        return extractClaim(token, Claims::getSubject);
//    }

//    public <T> T extractClaim(String token, Function<Claims,T> claimsResolver)
//    {
//        final Claims claims=extractAllClaims(token);
//        return claimsResolver.apply(claims);
//    }
//
//    private Claims extractAllClaims(String token)
//    {
//        return Jwts
//                .parserBuilder()
//                .setSigningKey(getSignInKey())
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//    }
//
//    private Key getSignInKey() {
//       byte[] keyBytes= Decoders.BASE64.decode(secretKey);
//       return Keys.hmacShaKeyFor(keyBytes);
//    }
//
//    // Token generation function
//
//    public long getExpirationTime() {
//        return JWT_EXPIRATION;
//    }
//
//    public String generateToken(UserDetails userDetails) {
//        return generateToken(new HashMap<>(), userDetails);
//    }
//
//   public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
//        return buildToken(extraClaims, userDetails, JWT_EXPIRATION);
//    }
//
//    private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, long JWT_EXPIRATION) {
//        String subject;
//        if (userDetails instanceof UserEntity user) {
//                subject = user.getEmail();
//            } else {
//                subject = userDetails.getUsername();
//            }
//
//        return Jwts
//                    .builder()
//                    .setClaims(extraClaims)
//                    .setSubject(subject)
//                    .setIssuedAt(new Date(System.currentTimeMillis()))
//                    .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
//                    .signWith(getSignInKey(), SignatureAlgorithm.HS256)
//                    .compact();
//    }
//
//    // Token validation function
//
//    public boolean isTokenValid(String token, UserDetails userDetails) {
//        final String email = extractUsername(token);
//        if (userDetails instanceof UserEntity user) {
//            return email.equals(user.getEmail()) && !isTokenExpired(token);
//        }
//        return false;
//    }
//
//    private boolean isTokenExpired(String token) {
//        return extractExpiration(token).before(new Date());
//    }
//
//    private Date extractExpiration(String token) {
//        return extractClaim(token, Claims::getExpiration);
//    }
}
