package hello.hello_spring.service;

import hello.hello_spring.domain.Member;
import hello.hello_spring.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class MemberService {

    // MemberService에서 사용하는 MemoryMemberRepository와 테스트케이스에서 사용하는 MemoryMemberRepository가 서로 다른 repository
//    private final MemberRepository memberRepository = new MemoryMemberRepository();

    // =>
    private final MemberRepository memberRepository;
    // 외부에서 넣어주도록 바꿈
    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    // => 생성자 주입

    // 회원가입
    public Long join(Member member) {

        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();

/*        long start = System.currentTimeMillis();


        try {
            validateDuplicateMember(member);    // 중복회원 검증

            // 같은 이름이 있는 중복회원 안될때

//        Optional<Member> result = memberRepository.findByName(member.getName());

*//*        // ifPresent 는 null이 아니라 값이 있으면 로직이 동작 (Optional이기 때문에 가능)
        result.ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });*//*

            memberRepository.save(member);
            return member.getId();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("join = " + timeMs);
        }*/
    }


    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    // 전체 회원 조회 기능
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
