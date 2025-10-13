package com.puzzlix.solid_task.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

public class UserRequest {

    // 회원 가입 UserRequest.SignUp a = UserRequest.SignUp()
    @Getter
    @Setter
    public static class SignUp {

        @NotEmpty
        @Size(min = 2, max = 20)
        private String name;

        @NotEmpty
        @Email
        private String email;

        @NotEmpty
        @Size(min = 4, max = 20)
        private String password;
    }

    // 로그인
    @Getter
    @Setter
    public static class Login {

        @NotEmpty
        @Email
        private String email;

        @NotEmpty
        private String password;
    }
}
