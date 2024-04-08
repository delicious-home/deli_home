package com.start.deli_home.Member.Entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailForm {
    @NotEmpty(message = "이메일은 필수항목입니다.")
    @Email
    private String email;
}