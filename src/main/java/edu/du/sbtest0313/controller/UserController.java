package edu.du.sbtest0313.controller;

import edu.du.sbtest0313.entity.Board;
import edu.du.sbtest0313.entity.User;
import edu.du.sbtest0313.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 회원가입 폼
    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("user", new User());
        return "login/signup";
    }

    // 회원가입 처리
    @PostMapping("/signup")
    public String signup(@ModelAttribute User user, Model model) {
        // 이메일 중복 확인
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            model.addAttribute("error", "이미 등록된 이메일입니다.");
            return "login/signup";
        }

        // 유저 이름 중복 확인
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            model.addAttribute("error", "이미 사용 중인 사용자 이름입니다.");
            return "login/signup";
        }

        // 저장
        userRepository.save(user);
        return "redirect:/user/login";
    }

    // 로그인 폼
    @GetMapping("/login")
    public String loginForm() {
        return "login/login";
    }

    // 로그인 처리
    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {

        return userRepository.findByEmail(email)
                .filter(user -> user.getPassword().equals(password))
                .map(user -> {
                    session.setAttribute("loginUser", user);
                    return "redirect:/";
                })
                .orElseGet(() -> {
                    model.addAttribute("error", "이메일 또는 비밀번호가 잘못되었습니다.");
                    return "login/login";
                });
    }

    // 로그아웃
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }






}
