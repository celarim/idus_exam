package com.example.idus_exam.user;

import com.example.idus_exam.user.model.Orders;
import com.example.idus_exam.user.model.User;
import com.example.idus_exam.user.model.UserDto;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }

    //회원 가입
    public ResponseEntity<String> signup(UserDto.SignupRequest dto) {
        User user = dto.toEntity();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok("Signup successful");
    }

    //유저 상세 정보
    public ResponseEntity<UserDto.DetailResponse> getUserDetail(User user) {
        User result = userRepository.findById(user.getIdx()).orElseThrow();
        return ResponseEntity.ok(UserDto.DetailResponse.from(result));
    }

    //유저 주문 목록
    public ResponseEntity<List<UserDto.OrdersResponse>> getUserOrders(User user) {
        User result = userRepository.findById(user.getIdx()).orElseThrow();
        List<Orders> ordersList = result.getOrdersList();
        return ResponseEntity.ok(UserDto.OrdersResponse.from(ordersList));
    }

    //유저 목록
    public ResponseEntity<List<UserDto.UserListResponse>> getUserList(int page, int size) {
        Page<User> users = userRepository.findAll(PageRequest.of(page, size));
        return ResponseEntity.ok(UserDto.UserListResponse.from(users.getContent()));
    }

    //유저 목록 이메일로
    public ResponseEntity<List<UserDto.UserListResponse>> getUserListByEmail(int  page, int size, String email) {
        Page<User> users = userRepository.findAllByEmailContaining(PageRequest.of(page, size), email);
        return ResponseEntity.ok(UserDto.UserListResponse.from(users.getContent()));
    }

    //유저 목록 이름으로
    public ResponseEntity<List<UserDto.UserListResponse>> getUserListByName(int  page, int size, String name) {
        Page<User> users = userRepository.findAllByNameContaining(PageRequest.of(page, size), name);
        return ResponseEntity.ok(UserDto.UserListResponse.from(users.getContent()));
    }
}
