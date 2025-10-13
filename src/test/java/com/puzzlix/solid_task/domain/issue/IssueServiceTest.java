package com.puzzlix.solid_task.domain.issue;

import com.puzzlix.solid_task.domain.issue.dto.IssueRequest;
import com.puzzlix.solid_task.domain.project.Project;
import com.puzzlix.solid_task.domain.project.ProjectRepository;
import com.puzzlix.solid_task.domain.user.User;
import com.puzzlix.solid_task.domain.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * 단위 테스트와 통합 테스트
 * - 단위 테스트
 */

// Mockito 프레임워크를 Junit5 와 연동하는 어노테이션
@ExtendWith(MockitoExtension.class) // 가짜 객체 생성
public class IssueServiceTest {

    @Mock
    private IssueRepository issueRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks // @Mock 객체들을 이곳에 주입
    private IssueService issueService;

    /**
     * 테스트 메서드 : 새로운 이슈 생성 기능 테스트
     * <p>
     * 테스트 시나리오
     * 1. 클라이언트가 이슈 생성 요청 보냄
     * 2. 서비스는 필요한 연관 엔티티들을 조회 (User, Project)
     * 3. 새로운 Issue 객체를 생성하고 연관관계를 설정
     * 4. Issue 를 저장함
     */

    @Test
    @DisplayName("새로운 이슈 생성 테스트(연관관계 포함)")
    void createIssue_withMapping() {
        // given(주어진 상황)
        // 1.클라이언트에 요청을 dto로 준비
        IssueRequest.Create request = new IssueRequest.Create();
        request.setTitle("이슈 생성 테스트 확인");
        request.setDescription("단위 테스트 통과 여부 확인");
        request.setProjectId(1L);
        request.setReporterId(1L);

        // 2. Repository 조회 시 반환될 가짜 객체 엔티티 준비
        User mockReporter = new User(1L, "가나디", "test@test.com", "1234", null);
        Project mockProject = new Project(1L, "solid task 프로젝트", "프로젝트 입니다", null);

        // Mock 객체는 기본적으로 빈 껍데기. 실제 동작처럼 시나리오를 미리 작성해주어야 함
        // 실제로 정보가 존재 하는지 확인
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockReporter));
        when(projectRepository.findById(1L)).thenReturn(Optional.of(mockProject));
        when(issueRepository.save(any(Issue.class)))
                .thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        // when
        Issue createIssue = issueService.createIssue(request);

        // then
        Assertions.assertThat(createIssue.getTitle()).isEqualTo("이슈 생성 테스트 확인");
        Assertions.assertThat(createIssue.getIssueStatus()).isEqualTo(IssueStatus.TODO);

        // 연관관계가 올바르게 설정 되었는지 객체 자체를 비교해서 검증
        Assertions.assertThat(createIssue.getReporter()).isEqualTo(mockReporter);
        Assertions.assertThat(createIssue.getProject()).isEqualTo(mockProject);

        // 추가 검증 - 서비스 로직이 실제로 findById 를 호출 했는지 확인
        verify(userRepository).findById(1L);
        verify(projectRepository).findById(1L);
    }

}
