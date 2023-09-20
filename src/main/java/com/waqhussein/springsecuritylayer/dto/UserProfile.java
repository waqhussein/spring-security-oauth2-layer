package com.waqhussein.springsecuritylayer.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserProfile {
    private String fullName;
    private String emailAddress;
    private String pictureUrl;
}
