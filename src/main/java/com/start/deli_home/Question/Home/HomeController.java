package com.start.deli_home.Question.Home;

import com.start.deli_home.Question.Entity.Question;
import com.start.deli_home.Question.Service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final QuestionService questionService;

    @GetMapping("/")
    public String home(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "category", required = false) String category) {
        Page<Question> paging = this.questionService.getList(page, category);
        model.addAttribute("paging", paging);
        model.addAttribute("selectedCategory", category); // 선택된 카테고리를 모델에 추가
        return "home"; // 홈페이지의 뷰 이름으로 변경해야 할 수 있습니다.
    }
}
