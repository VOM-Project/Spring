package vom.spring.global.jwt;

import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {
    private final String secretKey;
    @Value("${jwt.expiration_time}")
    private Long accessTokenValidTime;
    private final CustomUserDetailsService customUserDetailsService;
    private static final String access_header = "Authorization";

    //secret key base64 인코딩
    private JwtTokenProvider(@Value("${jwt.secret}") String secretKey, CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
        this.secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createAccessToken(Long userId, String email) {
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam("type", "jwt")
                .claim("id",userId)
                .claim("email", email)
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenValidTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    /**
     * access token 검증
     */
    public boolean validateAccessToken(String accessToken) {
        try {
            parseToken(accessToken);
        } catch (ExpiredJwtException e) {
            System.out.println("에러메세지 : "+ e.getMessage()) ;
        } catch (SignatureException | UnsupportedJwtException e) {
            System.out.println("에러메세지 : "+ e.getMessage()) ;
        }
        return true;
    }

    /**
     * claims 추출
     */
    public Claims parseToken(String token) {
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        return claims;
    }

    public boolean isExpired(String token, Date date) {
        try {
            Claims claims = parseToken(token);
            return claims.getExpiration().before(date); //만료시간이 만료되었다면 true, 만료되지 않았다면 false 리턴
        } catch (Exception e) {
            System.out.println("에러메세지 : "+ e.getMessage()) ;
        }
        return true; //default를 만료되었다고 하자
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(getUserPk(token));
        if (userDetails == null) {
            throw new UsernameNotFoundException("User not found for useremail: " + getUserPk(token));
        }
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    //header에서 access token 가져오기
    public String resolveAccessToken(HttpServletRequest request) {
        if (request.getHeader(access_header) != null && request.getHeader(access_header).startsWith("Bearer ")) {
            return request.getHeader(access_header).substring(7);
        }
        return null;
    }

    //member email 검색
    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody().get("email").toString();
    }

    //member id 검색
    public Long getMemberId(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody().get("id", Long.class);
    }

    /**
     * 토큰 갱신 - 일단은 갱신 로직은 리프레시 토큰 추가해서 추후 수정 : 6/14
     */

}
