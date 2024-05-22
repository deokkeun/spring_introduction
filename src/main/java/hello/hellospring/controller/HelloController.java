package hello.hellospring.controller;

import hello.hellospring.HelloSpringApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    /**
     * 스프링 부트 ( 내장 톰캣 서버 + 스프링 컨테이너 )
     * 스프링 부트에 내장 톰캣 서버를 통해 스프링 컨테이너로 전달한다.
     *
     * 웹 브라우저
     * -> 내장 톰캣 서버
     * -> 스프링 컨테이너 ( HelloController(return: hello) -> viewResolver(template/hello.html == Thymeleaf 템플릿 엔진 처리))
     * -> 웹 브라우저
     *
     * 컨트롤러에서 리턴 값으로 문자를 반환하면 뷰 리졸버(`viewResolver`)가 화면을 찾아서 처리한다.
     * 스프링 부트 템플릿엔진 기본 viewName 매핑
     * `resources:template/` + {ViewName} + `.html`
     * */
    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("data", "hello!");
        return "hello";
    }

    /**
     * 정적 컨텐츠 → 서버에서 하는것 없이 파일을 그대로 웹 브라우저로 내려주는 것
     * MVC와 템플릿 엔진 → 서버에서 프로그래밍해서 html 동적으로 웹 브라우저에 내려주는 것
     * API → (JSON)데이터 구조 포맷으로 클라이언트에게 데이터를 내려주는 것
     *
     * 정적 컨텐츠
     * 스프링 컨테이너에 hello-static 관련 컨트롤러가 없어서
     * 내장 톰캣 서버는 resource: static/hello-static.html 을 찾아 반환한다.
     */

    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam(value = "name", required = false) String name, Model model) {
        model.addAttribute("name", name);
        return "hello-template";
    }


    /**
     * @ResponseBody
     * HttpMessageConverter ( JsonConverter(객체), StringConverter(문자) ) == viewResolver
     *
     * HTTP의 BODY에 문자 내용을 직접 반환
     * `viewResolver` 대신에 `HttpMessageConverter` 가 동작
     * 기본 문자처리: `StringHttpMessageConverter`
     * 기본 객체처리: `MappingJackson2HttpMessageConverter`, Gson도 있음
     * byte 처리 등등 기타 여러 HttpMessageConverter가 기본으로 등록되어 있음
     *
     * 클라이언트의 HTTP Accept 해더와 서버의 컨트롤러 반환 타입 정보 둘을 조합해서
     * `HttpMessageConverter` 가 선택된다.
     */
    @GetMapping("hello-string")
    @ResponseBody // http 통신 프로토콜의 body에 직접 데이터를 넣어주겠다.
    public String helloString(@RequestParam("name") String name) {
        return "hello " + name; // hello spring!!!
    }

    @GetMapping("hello-api")
    @ResponseBody // 객체로 리턴하는 경우 body에 Json 형태로 표현한다.
    public Hello helloapi(@RequestParam("name") String name) {
        Hello hello = new Hello(); // command + shift + enter
        hello.setName(name);
        return hello; // Json 형태
    }

    static class Hello{ // 프로퍼티 접근 방식
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
