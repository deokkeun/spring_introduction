package hello.hello_spring.repository;

import hello.hello_spring.domain.Member;
import jakarta.persistence.EntityManager;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


// 스프링은 해당 클래스의 메서드를 실행할 때 트랜잭션을 시작하고, 메서드가 정상 종료되면 트랜잭션을 커
// 밋한다. 만약 런타임 예외가 발생하면 롤백한다.
// **JPA를 통한 모든 데이터 변경은 트랜잭션 안에서 실행해야 한다.**
@Transactional
public class JpaMemberRepository implements MemberRepository {

    private EntityManager em;

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
         List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class) // JPQL
                .setParameter("name", name)
                .getResultList();

         return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }
}
