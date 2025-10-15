package com.puzzlix.solid_task.domain.issue.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 실시간 채팅 메세지를 담기 위한 dto
 */
@Getter
@Setter
public class ChatMessageDto {
    private Long issueId; // 메세지가 속한 이슈 ID
    private String sender; // 메세지 보낸 사람
    private String  content; // 메세지 내용
}
