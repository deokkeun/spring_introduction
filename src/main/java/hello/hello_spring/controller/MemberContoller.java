package hello.hello_spring.controller;

import hello.hello_spring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller // 스프링 컨테이너 내부에 Spring bean(MemberContoller 객체)이 관리 된다고 표현합니다
public class MemberContoller {

    // 참고: DI에는 필드 주입, setter 주입, 생성자 주입 이렇게 3가지 방법이 있다.
    // 의존관계가 실행중에 동적으로 변하는 경우는 거의 없으므로 생성자 주입을 권장한다.
    private final MemberService memberService;

    // 주의: `@Autowired` 를 통한 DI는 `helloController` , `memberService` 등과 같이 스프링이 관리하
    // 는 객체에서만 동작한다. 스프링 빈으로 등록하지 않고 내가 직접 생성한 객체에서는 동작하지 않는다.
    @Autowired // DI (Dependency Injection)
    public MemberContoller(MemberService memberService) {
        this.memberService = memberService;
    }

    // 스프링 빈을 등록하는 2가지 방법

    // 1. 컴포넌트 스캔과 자동 의존관계 설정
    //    컴포넌트 스캔 원리
    // `@Component` 애노테이션이 있으면 스프링 빈으로 자동 등록된다.
    //     `@Controller` 컨트롤러가 스프링 빈으로 자동 등록된 이유도 컴포넌트 스캔 때문이다.
    // `@Component` 를 포함하는 다음 애노테이션도 스프링 빈으로 자동 등록된다.
    //     `@Controller`
    //     `@Service`
    //     `@Repository`

    // @SpringBootApplication에 작성된 패키지와 그 하위 패키지에 있는
    // @Component, @Service, @Repository, @Controller 등의 어노테이션이 붙은 클래스들은 자동으로 스프링 빈으로 등록됩니다.

    // 참고: 스프링은 스프링 컨테이너에 스프링 빈을 등록할 때, 기본으로 싱글톤으로 등록한다(유일하게 하나만
    // 등록해서 공유한다) 따라서 같은 스프링 빈이면 모두 같은 인스턴스다. 설정으로 싱글톤이 아니게 설정할 수 있지
    // 만, 특별한 경우를 제외하면 대부분 싱글톤을 사용한다.

    // 2. 자바 코드로 직접 스프링 빈 등록하기
    // SpringConfig 클래스를 만들어 @Configuration 어노태이션을 이용해 등록한다.
    // @Bean 메소드를 만들어 스프링 빈을 등록한다.

}
