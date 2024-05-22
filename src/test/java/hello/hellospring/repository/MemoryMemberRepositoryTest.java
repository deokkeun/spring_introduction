package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

public class MemoryMemberRepositoryTest {
    MemoryMemberRepository repository = new MemoryMemberRepository();

    // 테스트는 순서와 의존 관계 없이 설계가 되어야 한다.
    // 그러기 위해서 하나의 테스트가 끝날때 마다 저장소나 공용 데이터들을 깔끔하게 지워줘야 한다.
    // 테스트 메서드 실행이 끝날때 마다 동작 (clear)
    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }

    @Test
    public void save() {
        Member member = new Member();
        member.setName("spring");

        repository.save(member);

        // 변수 만들기 (option + command + v)
        Member result = repository.findById(member.getId()).get();
        // 검증 (글자로 볼수만 없다 -> Assertions.assertThat())
        // System.out.println("result = " + (result == member));

        // Assertions 는 static import 가능
        // import static org.assertj.core.api.Assertions.*;
        assertThat(member).isEqualTo(result);
    }

    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        // 변수명 한번에 변경 (shift + fn + F6)
        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        Member result = repository.findByName("spring1").get();

        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);
    }
}
