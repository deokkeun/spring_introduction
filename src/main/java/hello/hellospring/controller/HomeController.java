package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    /**
     * 내장 톰캣 서버는 스프링 컨테이너에 등록된 관련 컨트롤러를 먼저 찾고,
     * 없으면 static 파일에 index.html을 찾는다.
     * @return
     */
    @GetMapping("/")
    public String home() {
        return "home";
    }
}
