package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

/**
 * 서비스는 비즈니스에 의존족인 메서드 명을 사용해야 한다.
 * 테스트 케이스 생성 (command + shift + t)
 */
@Transactional // JPA 사용 시 Data를 저장하거나 변경할 때, 있어야 한다(해당 메서드에만 사용 가능)
// JPA 는 모든 Data 변경이 Transactional 안에서 실행 되어야 한다
public class MemberService {

    // MemberService와 MemberServiceTest 에 사용하는 MemoryMemberRepository 는 서로 다른 인스턴스가 되기 때문에,
    // 생성자로 변경
    // private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final MemberRepository memberRepository;

    // 생성자를 통해 외부에서 넣어주도록 변경
    // DI (Dependency Injection)
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원 가입
     */
    public Long join(Member member) {
        // 같은 이름이 있는 중복 회원X
        // 로직이 있는 경우 메서드로 뽑는게 좋다 (control + t(Refactor) -> 7. Extract Method...(option + command + m))
        validateDuplicateMember(member); // 중복 회원 검증

        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
