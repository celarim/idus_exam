package com.example.idus_exam.user;

import com.example.idus_exam.user.model.Orders;
import com.example.idus_exam.user.model.User;
import com.example.idus_exam.user.model.UserDto;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
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

    public ResponseEntity<String> signup(UserDto.SignupRequest dto) {
        User user = dto.toEntity();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok("Signup successful");
    }

    public ResponseEntity<UserDto.DetailResponse> getUserDetail(User user) {
        User result = userRepository.findById(user.getIdx()).orElseThrow();
        return ResponseEntity.ok(UserDto.DetailResponse.from(result));
    }

    public ResponseEntity<List<UserDto.OrdersResponse>> getUserOrders(User user) {
        User result = userRepository.findById(user.getIdx()).orElseThrow();
        List<Orders> ordersList = result.getOrdersList();
        return ResponseEntity.ok(UserDto.OrdersResponse.from(ordersList));
    }
}
