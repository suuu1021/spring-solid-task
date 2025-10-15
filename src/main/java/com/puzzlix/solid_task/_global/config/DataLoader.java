package com.puzzlix.solid_task._global.config;

import com.puzzlix.solid_task.domain.issue.Issue;
import com.puzzlix.solid_task.domain.issue.IssueRepository;
import com.puzzlix.solid_task.domain.issue.IssueStatus;
import com.puzzlix.solid_task.domain.project.Project;
import com.puzzlix.solid_task.domain.project.ProjectRepository;
import com.puzzlix.solid_task.domain.user.Role;
import com.puzzlix.solid_task.domain.user.User;
import com.puzzlix.solid_task.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component // IoC 대상
@RequiredArgsConstructor
@Profile("local")
@Order(1)
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final IssueRepository issueRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        User testUser1 = userRepository.save(new User(null, "홍길동",
                "test1@naver.com",
                passwordEncoder.encode("1234"),
                Role.USER,
                new ArrayList<>()));

        User testUser2 = userRepository.save(new User(null, "이순신",
                "test2@naver.com",
                passwordEncoder.encode("1234"),
                Role.USER,
                new ArrayList<>()));

        User testUser3 = userRepository.save(new User(null, "관리자",
                "test3@naver.com",
                passwordEncoder.encode("1234"),
                Role.ADMIN,
                new ArrayList<>()));

        Project testProject = projectRepository
                .save(new Project(null, "SOLID Task 프로젝트",
                        "SOLID 개념 학습"));

        issueRepository.save(new Issue(null, "로그인 기능 구현",
                "JWT 필요",
                IssueStatus.TODO, testProject, testUser1, null, new ArrayList<>()));

        issueRepository.save(new Issue(null, "검색 기능 구현 요청",
                "이슈 전체 목록에 검색 기능이 필요합니다",
                IssueStatus.TODO, testProject, testUser2, null, new ArrayList<>()));

    }
}