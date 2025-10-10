package com.puzzlix.solid_task._global.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 모든 API 응답을 감싸는 공통 DTO 설계
 */

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonResponseDto<T> {
    private boolean success;
    private T data;
    private String message;

//    이걸로 대체 @AllArgsConstructor(access = AccessLevel.PRIVATE)
//    private CommonResponseDto(boolean success, T data, String message) {
//        this.success = success;
//        this.data = data;
//        this.message = message;
//    }

    // 정적 팩토리 메서드 (팩토리 패턴과 다른 개념)
    // static 클래스에 포함 (객체 속성 X) - ClassName.add();
    public static <T> CommonResponseDto<T> success(T data, String message) {
        return new CommonResponseDto<>(true, data, message);
    }

    public static <T> CommonResponseDto<T> success(T data) {
        return success(data, null);
    }

    // 실패 응답을 생성하는 정적 팩토리 메서드
    public static <T> CommonResponseDto<T> error(String message) {
        return new CommonResponseDto<>(false, null, message);
    }

    /**
     * 클라이언트 코드 (Controller)로 부터 객체 생성 과정을 완전히 분리하고 숨기는 것이 목표
     * 이는 주로 OCP(개방-폐쇄 원칙)을 만족시키는 코드이다.
     */
    // new CommonResponseDto(); <- private 생성자
    // CommonResponseDto.error("잘못된 비밀번호");
}
