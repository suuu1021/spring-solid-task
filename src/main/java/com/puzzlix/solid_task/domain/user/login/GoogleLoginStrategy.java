package com.puzzlix.solid_task.domain.user.login;

import com.puzzlix.solid_task.domain.user.User;
import com.puzzlix.solid_task.domain.user.UserRepository;
import com.puzzlix.solid_task.domain.user.dto.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 구글 로그인
 */

@RequiredArgsConstructor
@Component
public class GoogleLoginStrategy implements LoginStrategy {

    private final UserRepository userRepository;

    @Override
    public User login(UserRequest.Login request) {
        // TODO - 구글 로그인 처리
        // 1. 클라이언트에게 받은 구글 토큰 가져옴
        // 2. 구글 서버로부터 사용자 정보 (이메일, 이름)
        // 3. 해당 이메일을 우리 DB 조회 후 없으면 자동 회원 가입 있으면 로그인 처리
        return null;
    }

    @Override
    public boolean supports(String type) {
        return "GOOGLE".equalsIgnoreCase(type);
    }
}
