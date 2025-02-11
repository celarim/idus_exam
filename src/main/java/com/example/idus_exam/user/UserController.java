package com.example.idus_exam.user;

import com.example.idus_exam.user.model.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Operation(summary = "회원 가입", description = "이름, 별명, 비밀번호, 전화번호, 이메일, 성별을 입력받아 회원 가입한다.")
    @Transactional
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserDto.SignupDto userDto) {
        return userService.signup(userDto);
    }
}
