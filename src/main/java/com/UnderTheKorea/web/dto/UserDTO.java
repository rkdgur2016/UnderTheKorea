package com.UnderTheKorea.web.dto;

import java.time.LocalDateTime;


import org.springframework.data.annotation.Id;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name="users")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserDTO {

    @Id // Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // PostgreSQL의 SERIAL에 해당
    @Column(name = "serial_no") // 컬럼 이름
    private Long serialNo;

    @Column(name = "user_id", unique = true, nullable = false, length = 50)
    private String userId;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "username", nullable = false, length = 100)
    private String username;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist // 엔티티가 처음 영속화될 때 실행
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate // 엔티티가 업데이트될 때 실행
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}