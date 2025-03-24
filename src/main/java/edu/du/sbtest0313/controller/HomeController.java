package edu.du.sbtest0313.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import edu.du.sbtest0313.entity.User;
import javax.servlet.http.HttpSession;

@Controller
public class HomeController {


    @GetMapping("/")
    public String index(HttpSession session, Model model) {
        // 세션에서 로그인된 사용자 정보 가져오기
        User loginUser = (User) session.getAttribute("loginUser");

        // Thymeleaf에서 사용할 수 있도록 모델에 추가
        model.addAttribute("loginUser", loginUser);

        return "index"; // resources/templates/index.html
    }
}
