package com.example.idus_exam.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

//        public static User toEntity(String email, String password) {
//            return User.builder()
//                    .email(email)
//                    .password(password)
//                    .build();
//        }
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DetailResponse {
        private Long idx;
        private String name;
        private String email;
        private String phoneNumber;
        private String nickname;
        private String gender;

        public static DetailResponse from(User user) {
            return DetailResponse.builder()
                    .idx(user.getIdx())
                    .name(user.getName())
                    .email(user.getEmail())
                    .phoneNumber(user.getPhoneNumber())
                    .nickname(user.getNickname())
                    .gender(user.getGender())
                    .build();
        }
    }


}
