package com.waqhussein.springsecuritylayer.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;


@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "USER_DETAILS")
public class UserDetailsEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "USER_DETAILS_ID", nullable = false)
    private String userDetailsId;
    @Column(name = "EMAIL_ADDRESS", nullable = false)
    private String emailAddress;
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "CREATED_AT")
    private Timestamp createdAt;
    @Column(name = "LAST_LOGIN_TIME")
    private Timestamp lastLoginTime;

}
