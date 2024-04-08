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

}
