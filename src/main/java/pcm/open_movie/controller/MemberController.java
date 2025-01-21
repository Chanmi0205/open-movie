package pcm.open_movie.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pcm.open_movie.domain.dto.MovieReserveRoomDTO;
import pcm.open_movie.domain.dto.MovieReserveSiteDTO;
import pcm.open_movie.domain.entity.Member;
import pcm.open_movie.controller.form.member.MemberForm;
import pcm.open_movie.controller.form.member.LoginMemberForm;
import pcm.open_movie.controller.form.member.QuitMemberForm;
import pcm.open_movie.controller.form.member.SettingMemberForm;
import pcm.open_movie.service.MemberService;
import pcm.open_movie.service.MovieReserveService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static pcm.open_movie.config.SessionConst.LOGIN_MEMBER_ID;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MovieReserveService movieReserveService;

        @GetMapping("/signUp")
    public String signUp(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "member/signUp";
    }

    @PostMapping("/signUp")
    public String signUp(@Validated @ModelAttribute(name = "memberForm") MemberForm memberForm,
                         BindingResult bindingResult, @RequestParam(value = "redirectURI", defaultValue = "/focus") String redirectURI,
                         HttpServletRequest request) {

        // 입력한 두 비밀번호가 일치하지 않을 경우
        if(!memberForm.getPassword().equals(memberForm.getCheckPassword()))
            bindingResult.rejectValue("pwNotSame", "pwNotSame");

        // 아이디가 존재할 경우
        if(memberService.memberExist(memberForm.getMemberId()))
            bindingResult.rejectValue("memberId", "memberIdSame");

        if (bindingResult.hasErrors()) return "member/signUp";

        Member joinMember = memberService.memberJoin(memberForm.getMemberId(), memberForm.getMemberName(),
                                                                memberForm.getPassword(), memberForm.getPhoneNum());

        HttpSession session = request.getSession(true);
        session.setAttribute(LOGIN_MEMBER_ID, joinMember.getMemberId());

        return "redirect:" + redirectURI;

    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("loginMemberForm", new LoginMemberForm());
        return "member/login";
    }
    
    @PostMapping("/login")
    public String login(@Validated @ModelAttribute("loginMemberForm") LoginMemberForm loginMemberForm,
                        BindingResult bindingResult, @RequestParam(value = "redirectURI", defaultValue = "/focus") String redirectURI,
                        HttpServletRequest request) {

        // 회원 아이디가 존재하지 않을 경우
        if(!memberService.memberExist(loginMemberForm.getMemberId()))
            bindingResult.rejectValue("memberId", "loginMemberNotExist");

        // 비밀번호가 일치하지 않을 경우(아이디는 이미 존재한다는 전재가 전 코드에 있음)
        if(memberService.memberExist(loginMemberForm.getMemberId(), loginMemberForm.getPassword()))
            bindingResult.rejectValue("memberId", "pwNotSame");

        if (bindingResult.hasErrors()) return "member/login";

        HttpSession session = request.getSession(true);
        session.setAttribute(LOGIN_MEMBER_ID, loginMemberForm.getMemberId());

        return "redirect:" + redirectURI;

    }

    @GetMapping("/quit")
    public String quitMember(Model model, HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        String loginMemberId = (String) session.getAttribute(LOGIN_MEMBER_ID);

        QuitMemberForm quitMemberForm = new QuitMemberForm();
        quitMemberForm.setMemberId(loginMemberId);

        model.addAttribute("quitMemberForm", quitMemberForm);
        return "member/quit";
    }

    @PostMapping("/quit")
    public String quitMember(@Validated @ModelAttribute("quitMemberForm") QuitMemberForm quitMemberForm,
                             BindingResult bindingResult, @RequestParam(value = "redirectURI", defaultValue = "/focus") String redirectURI,
                             HttpServletRequest request) {

        // 회원 아이디와 비밀번호가 일치하는 데이터가 없는 경우
        if(!memberService.memberExist(quitMemberForm.getMemberId(), quitMemberForm.getPassword()))
            bindingResult.rejectValue("memberId", "PWNotSame");

        if (bindingResult.hasErrors()) return "member/quit";

        memberService.memberQuit(quitMemberForm.getMemberId());
        HttpSession session = request.getSession(false);
        session.removeAttribute(LOGIN_MEMBER_ID);

        return "redirect:" + redirectURI;
    }

    @GetMapping("/setting")
    public String setting(Model model, HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        String settingMemberId = (String) session.getAttribute(LOGIN_MEMBER_ID);

        Member member = memberService.findMemberById(settingMemberId);

        SettingMemberForm settingMemberForm = new SettingMemberForm();
        settingMemberForm.defaultSetting(member.getMemberId(), member.getMemberName(), member.getPhoneNum());

        model.addAttribute("settingMemberForm", settingMemberForm);
        return "member/setting";
    }

    @PostMapping("/setting")
    public String setting(@Validated @ModelAttribute("settingMemberForm") SettingMemberForm settingMemberForm,
                          BindingResult bindingResult, @RequestParam(value = "redirectURI", defaultValue = "/focus") String redirectURI,
                          HttpServletRequest request) {

        // 비밀번호를 입력하지 않은 경우
        if (settingMemberForm.getPassword() == null && settingMemberForm.getCheckPassword() == null) {
            memberService.memberSetting
                    (settingMemberForm.getMemberId(), settingMemberForm.getMemberName(), settingMemberForm.getPhoneNum());
        }

        // 비밀번호 또한 변경하려 할 때, 비밀번호를 두 번 입력하였는지, 입력한 두 비밀번호가 일치하는지
        else if(settingMemberForm.getPassword() == null || settingMemberForm.getCheckPassword() == null ||
                settingMemberForm.getPassword().isEmpty() || settingMemberForm.getCheckPassword().isEmpty() ||
                !settingMemberForm.getPassword().equals(settingMemberForm.getCheckPassword())) {
            bindingResult.rejectValue("password", "passwordNull");
        } else {
            memberService.memberSetting
                    (settingMemberForm.getMemberId(), settingMemberForm.getMemberName(), settingMemberForm.getPhoneNum(), settingMemberForm.getPassword());
        }

        if (bindingResult.hasErrors()) return "member/setting";

        HttpSession session = request.getSession(false);
        session.setAttribute(LOGIN_MEMBER_ID, settingMemberForm.getMemberId());
        return "redirect:" + redirectURI;

    }

    @GetMapping("/exit")
    public String exit(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        session.removeAttribute(LOGIN_MEMBER_ID);
        return "redirect:/focus";
    }

    @GetMapping("/reserveList")
    public String reserveList(
            @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
            @RequestParam(value = "openMovieTF") boolean openMovieTF,
            Model model, HttpServletRequest request) {

        model.addAttribute("openMovieTF", openMovieTF);

        int pageSize = 7;

        HttpSession session = request.getSession(false);

        String memberId = (String) session.getAttribute(LOGIN_MEMBER_ID);
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);

        Page<MovieReserveRoomDTO> movieReserveRoomDTOList = movieReserveService.memberMovieReserveList(memberId, openMovieTF, pageable);
        model.addAttribute("movieReserveRoomDTOList", movieReserveRoomDTOList);

        List<Long> openCinemaRoomIdList = new ArrayList<>();

        for (MovieReserveRoomDTO movieReserveRoomDTO : movieReserveRoomDTOList) {
            openCinemaRoomIdList.add(movieReserveRoomDTO.getOpenCinemaRoomId());
        }

        Map<Long, List<MovieReserveSiteDTO>> movieReserveSiteList
                = movieReserveService.movieReserveSiteList(memberId, openCinemaRoomIdList);
        model.addAttribute("movieReserveSiteList", movieReserveSiteList);

        return "member/reserveList";
    }

}
