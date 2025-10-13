package com.puzzlix.solid_task.domain.issue;

import com.puzzlix.solid_task.domain.comment.Comment;
import com.puzzlix.solid_task.domain.project.Project;
import com.puzzlix.solid_task.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString(exclude = {"project", "reporter", "assignee", "comment"})
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;
    // 보고자 (누가 요청 했는지)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_id")
    private User reporter;
    // 담당자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignee_id")
    private User assignee;

    @OneToMany(mappedBy = "issue", cascade = CascadeType.ALL)
    private List<Comment> comment;
}
