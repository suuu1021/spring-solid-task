package com.puzzlix.solid_task.domain.user;

import com.puzzlix.solid_task.domain.user.dto.UserRequest;
import com.puzzlix.solid_task.domain.user.login.LoginStrategy;
import com.puzzlix.solid_task.domain.user.login.LoginStrategyFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    // AppConfig 에 Bean 으로 등록된 객체를 가져 온다
    private final PasswordEncoder passwordEncoder;
    private final LoginStrategyFactory loginStrategyFactory;

    /**
     * 회원가입
     * 1. 이메일 중복 확인
     * 2. 사용자 비밀번호를 암호화 처리
     * 3. DB 저장 처리
     */
    public User signUp(UserRequest.SignUp request) {
        // 이메일 중복 확인
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 사용 중인 이메일 입니다");
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // DB 저장 처리
        User newUser = new User();
        newUser.setName(request.getName());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(encodedPassword);

        return userRepository.save(newUser);
    }

    /**
     * 로그인
     * 1. 이메일로 사용자 조회
     * 2. 암호화된 비밀번호와 사용자가 입력한 비밀번호 비교
     */
    @Transactional(readOnly = true)
    public User login(String type, UserRequest.Login request) {
        // 1. 팩토리에게 알맞은 로그인 전략 요청
        LoginStrategy strategy = loginStrategyFactory.findStrategy(type);

        // 2. 해당 전략 클래스를 선택하여 로그인 요청 완료
        return strategy.login(request);
    }
}
