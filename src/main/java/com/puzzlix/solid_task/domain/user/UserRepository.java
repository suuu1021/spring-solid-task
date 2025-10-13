package com.puzzlix.solid_task.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // 이메일 중복 여부 확인- 쿼리 메서드 기능 활용
    Optional<User> findByEmail(String email);
}
