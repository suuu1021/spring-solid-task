package com.puzzlix.solid_task._global.config;

import com.puzzlix.solid_task.domain.issue.Issue;
import com.puzzlix.solid_task.domain.issue.IssueRepository;
import com.puzzlix.solid_task.domain.issue.IssueStatus;
import com.puzzlix.solid_task.domain.project.Project;
import com.puzzlix.solid_task.domain.project.ProjectRepository;
import com.puzzlix.solid_task.domain.user.User;
import com.puzzlix.solid_task.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
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

    @Override
    public void run(String... args) throws Exception {
        // 샘플 데이터 추가
        // 유저 2개, 프로젝트 1개
        User testUser1 = userRepository.save(
                new User(null, "가나디", "test1@naver.com", "1234",new ArrayList<>()));
        User testUser2 = userRepository.save(
                new User(null, "밍밍이", "test2@naver.com", "1234",new ArrayList<>()));

        Project testProject = projectRepository.save(
                new Project(null, "solid task 프로젝트", "프로젝트 입니다",new ArrayList<>()));

        Issue issue1 = issueRepository.save(
                new Issue(null, "로그인 기능 구현", "JWT 필요", IssueStatus.TODO,
                        testProject, testUser1, null, new ArrayList<>()));

        Issue issue2 = issueRepository.save(
                new Issue(null, "검색 기능 구현", "이슈 전체 목록에 검색 기능 필요",
                        IssueStatus.TODO, testProject, testUser2, null, new ArrayList<>()));
    }
}
