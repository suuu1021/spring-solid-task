package com.puzzlix.solid_task.domain.issue;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Issue {
    // pk, 타이틀, 내용, 진행 상태

    private Long id;
    private String title;
    private String description;
    private IssueStatus issueStatus;

    // 추후 연관관계 필드
    // 프로젝트 pk
    private Long projectId;
    // 보고자 (누가 요청 했는지)
    private Long reporterId;
    // 담당자
    private Long assigneeId;
}
