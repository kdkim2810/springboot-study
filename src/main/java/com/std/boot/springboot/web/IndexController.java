package com.std.boot.springboot.web;

import com.std.boot.springboot.config.auth.LoginUser;
import com.std.boot.springboot.config.auth.dto.SessionUser;
import com.std.boot.springboot.service.posts.PostsService;
import com.std.boot.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {
    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {
        model.addAttribute("posts", postsService.findAllDesc());

//        로그인 성공 시 세션에 SessionUser를 저장
//        SessionUser user = (SessionUser) httpSession.getAttribute("user");

        // 세션에 저장된 값이 있을때만 userName으로 등록
        if(user != null) {
            model.addAttribute("userNameTest", user.getName());
        }

        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave(){
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }


}
