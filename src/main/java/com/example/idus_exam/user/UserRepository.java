package com.example.idus_exam.user;

import com.example.idus_exam.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
