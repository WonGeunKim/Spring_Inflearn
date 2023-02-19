package hello.hello_spring.controller;

import hello.hello_spring.domain.Member;
import hello.hello_spring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemberController {

    // new로 생성해서 쓸 수 있음
    // new로 하면 다른 컨트롤러들이 서비스를 가져다 쓸 수 있음
    // 여러개 생성할 필요x , 하나만 생성해서 쓰면 된다

    // 스프링이 관리를 하게 되면 스픠링 컨테이너에 등록을 하고 컨테이너로부터 받아서 쓰도록 바꿔야함
    //
//    private final MemberService memberService = new MemberService();

    // 스프링 컨테이너에 등록해놓고 쓰는게 나음
    // 컨테이너에 등록하면 딱 하나만 등록된다

//    @Autowired  // 필드 주입
    private final MemberService memberService;

    @Autowired  // 컨테이너에 있는 서비스를 스프링이 가져다가 연결해줌 => DI
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
    // 생성자를 통해서 주입 => 생성자 주입 - 권장


/*    // setter 주입
    @Autowired
    public void setMemberService(MemberService memberService) {
        this.memberService = memberService;
    }*/


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
