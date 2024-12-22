package hello.hello_spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("data", "hello!!");
        return "hello";
    }
    // 컨트롤러에서 리턴 값으로 문자를 반환하면 뷰 리졸버(`viewResolver`)가 화면을 찾아서 처리한다.
    // 스프링 부트 템플릿엔진 기본 viewName 매핑 `resources:templates/` +{ViewName}+ `.html`

    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam("name") String name, Model model) {
        model.addAttribute("name", name);
        return "hello-template";
    }

//    `@ResponseBody` 를 사용
//    HTTP의 BODY에 문자 내용을 직접 반환
//    `viewResolver` 대신에 `HttpMessageConverter` 가 동작
//    기본 문자처리: `StringHttpMessageConverter`
//    기본 객체처리: `MappingJackson2HttpMessageConverter`
//    byte 처리 등등 기타 여러 HttpMessageConverter가 기본으로 등록되어 있음
    @GetMapping("hello-string")
    @ResponseBody // HTTP 통신 프로토콜의 응답 Body 에 리턴값을 직접 넣어 반환
    public String helloString(@RequestParam("name") String name) {
        return "hello " + name; // StringConverter
    }

    @GetMapping("hello-api")
    @ResponseBody // Body에 리턴값으로 객체를 넣어주면 JSON 형태로 반환
    public Hello helloApi(@RequestParam("name") String name) {
        Hello hello = new Hello();
        hello.setName(name);
        return hello; // JsonConverter
    }

    static class Hello {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
