package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller // 스프링 컨테이너에서 스프링 빈이 관리된다.
public class MemberController {
    // command + e 최근에 본 목록

    /**
     * 스프링 빈을 등록하는 2가지 방법
     * 컴포넌트 스캔과 자동 의존관계 설정
     * @Component 애노테이션이 있으면 스프링 빈으로 자동으로 등록된다.
     * @Controller 컨트롤러가 스프링 빈으로 자동 등록된 이유도 컴포넌트 스캔 때문이다.
     *
     * @Controller를 포함하는 다음 애노테이션도 스프링 빈으로 등록된다.
     *      @Controller
     *      @Service
     *      @Repository
     *
     * 자바 코드로 직접 스프링 빈 등록하기(SpringConfig)
     * 실무에서는 주로 정형화된 컨트롤러, 서비스, 리포지토리 같은 코드는 컴포넌트 스캔을 사용한다.
     * 그리고 정형화 되지 않거나, 상황에 따라 구현 클래스를 변경해야 하면 설정을 통해 스프링 빈으로 등록한다.
     * EX) 아직 데이터 저장소가 선정되지 않음(가상의 시나리오)
     */

    // DI에는 필드 주입, setter 주입, 생성자 주입 이렇게 3가지 방법이 있다.
    // 의존관계가 실행중에 동적으로 변하는 경우는 거의 없으므로 생성자 주입을 권장한다.

    // @Autowired // 필드 주입(빈에 등록되면 중간에 변경하기가 힘들어서 비선호)
    private final MemberService memberService;

//    @Autowired // setter 주입(public 으로 해야되기 때문에 비선호)
//    public void setMemberService(MemberService memberService) {
//        this.memberService = memberService;
//    }

    @Autowired // 생성자 주입
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members/new")
    public String createForm() {
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());

        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
