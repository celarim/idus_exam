package com.example.idus_exam.user;

import com.example.idus_exam.user.model.User;
import com.example.idus_exam.user.model.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Operation(summary = "회원 가입", description = "이름, 별명, 비밀번호, 전화번호, 이메일, 성별을 입력받아 회원 가입한다.")
    @Transactional
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserDto.SignupRequest dto) {
        return userService.signup(dto);
    }


    @Operation(summary = "유저 상세 정보 반환", description = "ATOKEN을 가지고 있는 유저에게 해당 토큰을 바탕으로 비밀 번호를 제외한 회원 정보를 알려준다.")
    @Transactional(readOnly = true)
    @GetMapping("/detail")
    public ResponseEntity<UserDto.DetailResponse> detail(@AuthenticationPrincipal User user) {
        return userService.getUserDetail(user);
    }

    @Operation(summary = "유저 주문 목록 조회", description = "ATOKEN을 가지고 있는 유저에게 해당 토큰을 바탕으로 유저의 주문 목록을 전달한다.")
    @Transactional(readOnly = true)
    @GetMapping("/orders")
    public ResponseEntity<List<UserDto.OrdersResponse>> orders(@AuthenticationPrincipal User user) {
        return userService.getUserOrders(user);
    }

    @Operation(summary = "유저 목록 조회", description = "유저 목록을 페이지와 크기를 받아 전달한다")
    @Transactional(readOnly = true)
    @GetMapping("/list")
    public ResponseEntity<List<UserDto.UserListResponse>> list(int page, int size) {
        return userService.getUserList(page, size);
    }

    @Operation(summary = "유저 목록 조회 이메일로 검색", description = "이메일에서 전달받은 것을 포함하는 모든 유저 목록을 페이지와 크기를 받아 전달한다")
    @Transactional(readOnly = true)
    @GetMapping("/list/email")
    public ResponseEntity<List<UserDto.UserListResponse>> listEmail(int page, int size, String email) {
        return userService.getUserListByEmail(page, size, email);
    }

    @Operation(summary = "유저 목록 조회 이름으로 검색", description = "이름에서 전달받은 것을 포함하는 모든 유저 목록을 페이지와 크기를 받아 전달한다")
    @Transactional(readOnly = true)
    @GetMapping("/list/name")
    public ResponseEntity<List<UserDto.UserListResponse>> listName(int page, int size, String name) {
        return userService.getUserListByName(page, size, name);
    }
}
