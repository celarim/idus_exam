package com.example.idus_exam.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrdersResponse {
        private Long idx;
        private String orderNumber;
        private String productName;
        private LocalDateTime orderDate;

        public static OrdersResponse from(Orders orders) {
            return OrdersResponse.builder()
                    .idx(orders.getIdx())
                    .orderNumber(orders.getOrderNumber())
                    .productName(orders.getProductName())
                    .orderDate(orders.getOrderDate())
                    .build();
        }

        public static List<OrdersResponse> from(List<Orders> orders) {
            return orders.stream().map(OrdersResponse::from).collect(Collectors.toList());
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserListInstanceResponse {
        private Long idx;
        private String name;
        private String email;
        private String phoneNumber;
        private String nickname;
        private String gender;
        private OrdersResponse lastOrder;

        public static UserListInstanceResponse from(User user) {
            List<Orders> ordersList = user.getOrdersList();
            OrdersResponse lastOrder = null;
            if(!ordersList.isEmpty()) {
                Orders last = ordersList.get(0);
                for(Orders order : ordersList) {
                    if(order.getOrderDate().isAfter(last.getOrderDate())) {
                        last = order;
                    }
                }
                lastOrder = OrdersResponse.from(last);
            }

            return UserListInstanceResponse.builder()
                    .idx(user.getIdx())
                    .name(user.getName())
                    .email(user.getEmail())
                    .phoneNumber(user.getPhoneNumber())
                    .nickname(user.getNickname())
                    .gender(user.getGender())
                    .lastOrder(lastOrder)
                    .build();
        }

        public static List<UserListInstanceResponse> from(List<User> users) {
            return users.stream().map(UserListInstanceResponse::from).collect(Collectors.toList());
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserListResponse {
        List<UserListInstanceResponse> users;
        private Integer totalPages;
        private Boolean hasNextPage;
        private Boolean hasPreviousPage;

        public static UserListResponse from(Page<User> users) {
            return UserListResponse.builder()
                    .users(UserListInstanceResponse.from(users.getContent()))
                    .totalPages(users.getTotalPages())
                    .hasNextPage(users.hasNext())
                    .hasPreviousPage(users.hasPrevious())
                    .build();
        }
    }


}
