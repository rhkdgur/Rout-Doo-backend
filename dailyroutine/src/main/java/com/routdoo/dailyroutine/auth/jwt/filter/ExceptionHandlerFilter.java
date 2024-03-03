package com.routdoo.dailyroutine.auth.jwt.filter;

import com.routdoo.dailyroutine.auth.jwt.JwtProvider;
import com.routdoo.dailyroutine.auth.jwt.JwtResultCodeType;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SecurityException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.jdbc.Null;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.routdoo.dailyroutine.auth.jwt.filter.JwtAuthenticationFilter.setErrorResponse;

/**
 * packageName    : com.routdoo.dailyroutine.auth.jwt.filter
 * fileName       : ExceptionHandlerFilter
 * author         : rhkdg
 * date           : 2024-03-01
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-03-01        rhkdg       최초 생성
 */
@Slf4j
@RequiredArgsConstructor
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = jwtProvider.resolveToken(request);
            jwtProvider.validateTokenCheck(token);
        }catch (SecurityException | MalformedJwtException e){
            request.setAttribute("exception",JwtResultCodeType.INVALID_TOKEN.name());
        }catch (ExpiredJwtException e) {
            request.setAttribute("exception",JwtResultCodeType.EXPIRED_TOKEN.name());
        }catch (UnsupportedJwtException e) {
            request.setAttribute("exception",JwtResultCodeType.TOKEN_NOTSUPPORT.name());
        }catch (IllegalArgumentException | NullPointerException e) {
            log.error("token is null or empty");
//            filterChain.doFilter(request, response);
        }
        filterChain.doFilter(request, response);
    }
}
