package com.example.idus_exam.user.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private String orderNumber;
    private String productName;
    private LocalDateTime orderDate;

    @ManyToOne
    @JoinColumn(name="user_idx")
    private User user;
}
