package com.puzzlix.solid_task.domain.issue;

import com.puzzlix.solid_task.domain.issue.dto.IssueRequest;
import com.puzzlix.solid_task.domain.project.Project;
import com.puzzlix.solid_task.domain.project.ProjectRepository;
import com.puzzlix.solid_task.domain.user.User;
import com.puzzlix.solid_task.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service // IoC
@RequiredArgsConstructor
@Transactional
public class IssueService {
    // 구체 클래스가 아닌 IssueRepository 라는 역할(인터페이스)에만 의존한다.
    private final IssueRepository issueRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    /**
     * JPA 사용 (수정 전략) --> 더티 체킹
     * JPA 변경 감지(Dirty Checking) 덕분에 save() 명시적으로 호출하지 않아도
     *  트랜잭션이 끝날 때 변경된 내용이 DB에 자등으로 반영 된다.
     */
    // 이슈 수정
    public Issue updateIssue(Long issueId, IssueRequest.Update request) {
        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new NoSuchElementException("해당 ID의 이슈를 찾을 수 없습니다"));
        // 넘어온 값이 담당자 할당 여부에 따로 분기 처리 되어야 함
        if (request.getAssigneeId() != null) {
            User assignee = userRepository.findById(request.getAssigneeId())
                    .orElseThrow(() -> new NoSuchElementException("해당 ID의 담당자를 찾을 수 없습니다"));
            // 담당자 할당
            issue.setAssignee(assignee);
        } else {
            // 담당자 해제 처리
            issue.setAssignee(null);
        }
        issue.setTitle(request.getTitle());
        issue.setDescription(request.getDescription());
        return issue;
    }

    // 이슈 삭제
    public void deleteIssue (Long issueId) {
        if (!issueRepository.existsById(issueId)) {
            throw new NoSuchElementException("해당 ID의 이슈를 찾을 수 없습니다");
        }
        issueRepository.deleteById(issueId);
    }

    // 이슈 생성 로직
    public Issue createIssue(IssueRequest.Create request) {

        // 보고자 ID -> 실제 회원이 있는지
        User reporter = userRepository.findById(request.getReporterId())
                .orElseThrow(() -> new NoSuchElementException("해당 ID의 사용자를 찾을 수 없습니다"));
        // 프로젝트 ID 검증
        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new NoSuchElementException("해당 ID의 프로젝트를 찾을 수 없습니다"));


        Issue newIssue = new Issue();
        newIssue.setTitle(request.getTitle());
        newIssue.setDescription(request.getDescription());
        newIssue.setProject(project);
        newIssue.setReporter(reporter);
        newIssue.setIssueStatus(IssueStatus.TODO); // 시스템에서 설정 (최초 등록)
        return issueRepository.save(newIssue);
    }

    // 모든 이슈 조회
    @Transactional(readOnly = true)
    public List<Issue> findIssues() {
        return issueRepository.findAll();
    }
}
