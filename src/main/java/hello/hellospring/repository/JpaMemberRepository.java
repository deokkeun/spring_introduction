package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository{

    private final EntityManager em;

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        em.persist(member); // 영속하다, 영구 저장하다
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        // jpql 객체지향쿼리
        // JPQL에서는 테이블 이름이 아닌 엔터티 클래스 이름(Member)을 사용해야 합니다.
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();

        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
//        List<Member> result = em.createQuery("select m from member m", Member.class)
//                .getResultList();
//        return result;
//        control + t (Refactor -> Inline Variable) == command + option + n
        return em.createQuery("select m from member m", Member.class)
                .getResultList();
    }
}
