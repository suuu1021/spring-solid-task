package com.puzzlix.solid_task.domain.issue;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Issue {
    // pk, 타이틀, 내용, 진행 상태

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private IssueStatus issueStatus;

    // 추후 연관관계 필드
    // 프로젝트 pk
    private Long projectId;
    // 보고자 (누가 요청 했는지)
    private Long reporterId;
    // 담당자
    private Long assigneeId;
}
