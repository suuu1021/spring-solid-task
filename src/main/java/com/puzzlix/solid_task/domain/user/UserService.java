package com.puzzlix.solid_task.domain.user;

import com.puzzlix.solid_task.domain.user.dto.UserRequest;
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
    public User login(UserRequest.Login request) {
        // 이메일 사용자 조회
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 이메일 입니다"));

        // 비밀번호 비교
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호 입니다");
        }
        return user;
    }
}
