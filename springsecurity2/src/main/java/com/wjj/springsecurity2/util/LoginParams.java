package com.wjj.springsecurity2.util;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginParams {
    private String username;
    private String password;
}
