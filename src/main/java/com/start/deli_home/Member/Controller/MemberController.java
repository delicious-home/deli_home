package com.start.deli_home.Member.Controller;


import com.start.deli_home.Member.Entity.EmailForm;
import com.start.deli_home.Member.Entity.Member;
import com.start.deli_home.Member.Form.MemberForm;
import com.start.deli_home.Member.Form.MemberModifyForm;
import com.start.deli_home.Member.Service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/login")
    public String login() {
        return "login_form";
    }

    @GetMapping("/signup")
    public String signup(MemberForm memberForm) {
        return "signup_form";
    }

    @PostMapping("/signup")
    public String signup(@Valid MemberForm memberForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup_form";
        }

        if (!memberForm.getPassword1().equals(memberForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect", "2개의 패스워드가 일치하지 않습니다.");
            return "signup_form";
        }

        try {
            memberService.create(memberForm.getUsername(), memberForm.getEmail(), memberForm.getPassword1());
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return "signup_form";
        } catch (Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "signup_form";
        }

        return "redirect:/question/list";
    }
    @GetMapping("/findId")
    public String showFindIdForm(EmailForm emailForm) {
        return "temp_password_form";
    }

    @PostMapping("/findId")
    public String memberFindId(Model model, @Valid EmailForm emailForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "temp_password_form";
        }
        Member member = this.memberService.findByEmail(emailForm.getEmail());
        if (member != null) {
            model.addAttribute("idFound", true);
            model.addAttribute("id", member.getUsername());
        }
        return "temp_password_form";
    }
    @GetMapping("/tempPassword")
    public String sendTempPassword(EmailForm emailForm) {
        return "temp_password_form";
    }

    @PostMapping("/tempPassword")
    public String sendTempPassword(@Valid EmailForm emailForm,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "temp_password_form";
        }

        try {
            memberService.modifyPassword(emailForm.getEmail());
        } catch (RuntimeException e) {
            e.printStackTrace();
            bindingResult.reject("emailNotFound", e.getMessage());
            return "temp_password_form";
        }
        return "redirect:/";
    }
    @GetMapping("/modify")
    public String modify (Principal principal,MemberModifyForm memberModifyForm) {
        Member member = this.memberService.getMember(principal.getName());
        memberModifyForm.setEmail(member.getEmail());
        memberModifyForm.setUsername(member.getUsername());
        memberModifyForm.setPassword(member.getPassword());

        return "edit_profile_form";
    }
    @PostMapping("/modify")
    public String modifyPassword(@RequestParam("username") String username,
                                 @RequestParam("newPassword") String newPassword) {
        memberService.modifyPassword(username, newPassword);
        return "redirect:/";
    }



}
