package com.puzzlix.solid_task.domain.issue.dto;

import com.puzzlix.solid_task.domain.issue.Issue;
import com.puzzlix.solid_task.domain.issue.IssueStatus;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class IssueResponse {

    // 모든 이슈 보여주기, 단일 이슈 보여주기
    @Getter
    public static class FindAll {
        private final Long id;
        private final String title;
        private final IssueStatus status;

        // 생성자를 private 선언
        private FindAll(Issue issue) {
            this.id = issue.getId();
            this.title = issue.getTitle();
            this.status = issue.getIssueStatus();
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
}
