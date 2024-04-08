package com.start.deli_home.Member.Form;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MemberModifyForm {


    private String username;
    private String password;


    @Email
    private String email;

    private String newPassword;


    private String confirmPassword;

    public String getNewPassword() {

        return newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }
}
