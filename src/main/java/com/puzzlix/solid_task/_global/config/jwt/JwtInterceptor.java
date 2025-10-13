package com.puzzlix.solid_task._global.config.jwt;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1. 요청 메세지 헤더에서 키값 (Authorization) 헤더를 찾아 JWT 토큰 추출
        String token = resolveToken(request);
        if (token != null && jwtTokenProvider.validateToken(token)) {
            // 결과 값이 true 라면 controller 로 보낸다
            return true;
        }
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "유효하지 않은 토큰 입니다");
        return false;
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        // 토큰 검증 및 "Bearer " (공백 한칸을 잘라냄)
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
