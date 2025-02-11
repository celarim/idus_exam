package com.example.idus_exam.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

public class UserDto {

    @Getter
    public static class SignupRequest {
        @Schema(example = "test")
        private String name;
        @Schema(example = "test@test.com")
        private String email;
        @Schema(example = "qwer1234")
        private String password;
        @Schema(example = "01012345678")
        private String phoneNumber;
        @Schema(example = "test")
        private String nickname;
        @Schema(example = "MALE")
        private String gender;

        public User toEntity() {
            return User.builder()
                    .name(name)
                    .email(email)
                    .password(password)
                    .phoneNumber(phoneNumber)
                    .nickname(nickname)
                    .gender(gender)
                    .role("ROLE_USER")
                    .build();
        }
    }

    @Getter
    public static class LoginRequest {
        @Schema(example = "test@test.com")
        private String email;
        @Schema(example = "qwer1234")
        private String password;

        public static User toEntity(String email, String password) {
            return User.builder()
                    .email(email)
                    .password(password)
                    .build();
        }
    }
}
