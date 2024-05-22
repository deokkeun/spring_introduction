package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository{

    /**
     * 공유되는 변수 일때는 동시성 문제가 발생할 수 있어서,
     * ConcurrentHashMap 을 사용해야 한다.
     * Long 의 경우 AutumnLong
     * AutumnLong 은 Long 자료형을 갖고 있는 Wrapping 클래스이다.
     */
    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream() // 람다
                .filter(member -> member.getName().equals(name)) // 파라미터 값과 같은 경우 필터링
                .findAny(); // 하나라도 찾는 것
        // 없으면 Optional 에 null 이 포함되서 반환
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }
}
