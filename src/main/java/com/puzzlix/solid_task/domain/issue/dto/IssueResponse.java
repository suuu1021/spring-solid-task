package com.puzzlix.solid_task.domain.issue.dto;

import com.puzzlix.solid_task.domain.issue.Issue;
import com.puzzlix.solid_task.domain.issue.IssueStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class IssueResponse {

    // 모든 이슈 보여주기, 단일 이슈 보여주기
    @Getter
    public static class FindAll {
        private final Long id;
        private final String title;
        private final IssueStatus status;
        private final String reporterName;

        // 생성자를 private 선언
        private FindAll(Issue issue) {
            this.id = issue.getId();
            this.title = issue.getTitle();
            this.status = issue.getIssueStatus();
            // 현재 LAZY 로딩 전략 - issue 안에 포함된
            // User 탐색하게 되면 추가 쿼리가 발생됨 -> N + 1 문제
            this.reporterName = issue.getReporter().getName();
        }

        // 정적 팩토리 메서드 선언(제네릭 타입 X)
        // IssueResponse.from([issue, issue]);
        // Entity 를 DTO로 변환하는 정적 팩토리 메서드를 만듬
        public static List<FindAll> from(List<Issue> issues) {
            List<FindAll> dtoList = new ArrayList<>();
            for (Issue issue : issues) {
                dtoList.add(new FindAll(issue));
            }
            return dtoList;
        }
    }

    @Getter
    @Setter
    public static class FindById {
        private final Long id;
        private final String title;
        private final String description;
        private final IssueStatus issueStatus;
        private final String projectName;
        private final String reporterName; // 보고자 이름
        private final String assigneeName; // 담당자 이름

        // Entity 를 DTO로 변환하는 생성자
        public FindById(Issue issue) {
            this.id = issue.getId();
            this.title = issue.getTitle();
            this.description = issue.getDescription();
            this.issueStatus = issue.getIssueStatus();
            this.projectName = issue.getProject().getName();
            this.reporterName = issue.getReporter().getName();
            // 담당자가 미정일 경우
            this.assigneeName = issue.getAssignee() != null ? issue.getAssignee().getName() : null;
        }
    }
}
