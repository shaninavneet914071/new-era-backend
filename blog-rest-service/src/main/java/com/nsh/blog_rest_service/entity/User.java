package com.nsh.blog_rest_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class User {
    @Id
    @JsonIgnore
    private UUID id;
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    @Column(name = "blogger_name", nullable = false, length = 100)
    private String bloggerName;
    private String userId;
    private String userType;
    private String email;
    private String about;

}
