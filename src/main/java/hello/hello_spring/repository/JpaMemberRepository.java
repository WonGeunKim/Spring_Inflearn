package hello.hello_spring.repository;

import hello.hello_spring.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository{

    // JPA는 EntityManager라는 것으로 모든 것이 동작
    private final EntityManager entityManager;

    public JpaMemberRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public Member save(Member member) {
        entityManager.persist(member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = entityManager.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        // jpql이라는 객체지향 쿼리 언어를 사용해야함

        List<Member> result = entityManager.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();

        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        // jpql이라는 객체지향 쿼리 언어를 사용해야함

        List<Member> result = entityManager.createQuery("select m from Member m", Member.class)
                .getResultList();
        return result;
    }
}
