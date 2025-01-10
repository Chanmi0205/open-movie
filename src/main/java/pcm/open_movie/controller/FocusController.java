package pcm.open_movie.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pcm.TestDataInit;
import pcm.open_movie.config.SessionConst;
import pcm.open_movie.controller.form.reserve.OpenCinemaRoomDateForm;
import pcm.open_movie.controller.form.reserve.OpenCinemaIdAndDateForm;
import pcm.open_movie.controller.form.reserve.ReserveForm;
import pcm.open_movie.domain.dto.OpenCinemaRoomAndSiteDTO;
import pcm.open_movie.domain.dto.OpenCinemaRoomDTO;
import pcm.open_movie.domain.dto.OpenCinemaRoomSiteDTO;
import pcm.open_movie.domain.dto.OpenCinemaRoomSiteSelectDTO;
import pcm.open_movie.domain.entity.*;
import pcm.open_movie.service.CinemaService;
import pcm.open_movie.service.MemberService;
import pcm.open_movie.service.MovieReserveService;
import pcm.open_movie.service.OpenMovieService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// main page(home),
// reserve service(open movie list, movie reserve service, reserve date, cinema room, cinema room site ... select)
@Controller
@RequestMapping("/focus")
@RequiredArgsConstructor
public class FocusController {

    private final MemberService memberService;
    private final OpenMovieService openMovieService;
    private final MovieReserveService movieReserveService;
    private final CinemaService cinemaService;

    // 상영 영화 선택(메인 페이지)
    @GetMapping
    public String focus(Model model, HttpServletRequest request) {

        List<OpenMovie> openMovieList = openMovieService.openMovieList();
        model.addAttribute("openMovieList", openMovieList);

        HttpSession session = request.getSession(false);

        if(session != null) {
            String loginMemberId = (String) request.getAttribute(SessionConst.LOGIN_MEMBER_ID);
            String memberName = memberService.findMemberName(loginMemberId);
            model.addAttribute("memberName", memberName);
        }

       return "focus/main";
    }

    // 상영일, 영화관 선택 (PCM 2024-10-05)
    @GetMapping("/{openMovieId}")
    public String openCinema(@PathVariable("openMovieId") Long openMovieId,
                             @RequestParam(value = "openCinemaRoomIdNull", required = false) String openCinemaRoomIdNull,
                             @RequestParam(value = "reserveDateNull", required = false) String reserveDateNull,
                             Model model, HttpServletRequest request) {

        String openCinemaRoomIdNull_errorText = "관람할 영화관을 선택해 주십시오.";
        String reserveDateNull_errorText = "관람할 날짜를 선택해 주십시오.";

        if(openCinemaRoomIdNull != null) {
            model.addAttribute("openCinemaRoomIdNull_errorText", openCinemaRoomIdNull_errorText);
        } else if(reserveDateNull != null) {
            model.addAttribute("reserveDateNull_errorText", reserveDateNull_errorText);
        }

        // 선택한 상영 영화
        OpenMovie openMovie = openMovieService.openMovieById(openMovieId).orElse(null);
        model.addAttribute("openMovie", openMovie);

        // 오픈 날짜, 상영관
        List<Cinema> openCinemaList = openMovieService.openCinemaList(openMovieId);
        model.addAttribute("openCinemaList", openCinemaList);

        // 수정해야할듯
        Map<Long, List<String>> openDateList = new ConcurrentHashMap<>();

        for (Cinema cinema : openCinemaList) {
            Long cinemaId = cinema.getCinemaId();
            List<String> dateList = openMovieService.openCinemaRoomCount(openMovieId, cinemaId);
            openDateList.put(cinemaId, dateList);
        }

        // 상영 영화관 ID, 해당 날짜, 해당 날짜에 상영하는 수
        model.addAttribute("openDateList", openDateList);
        model.addAttribute("openCinemaIdAndDateForm", new OpenCinemaIdAndDateForm());

        return "focus/reserve/openCinema";

    }

    // 상영 시간, 상영 관 선택 (1관 17:30)
    @PostMapping("/{openMovieId}")
    public String openCinemaRoomList(@PathVariable("openMovieId") Long openMovieId,
                                     @ModelAttribute("openCinemaIdAndDateForm") OpenCinemaIdAndDateForm openCinemaIdAndDateForm,
                                     Model model) {

        if(openCinemaIdAndDateForm.getOpenCinemaRoomId() == null) return "redirect:/focus/" + openMovieId + "?openCinemaRoomIdNull=true";
        if(openCinemaIdAndDateForm.getReserveDate() == null) return "redirect:/focus/" + openMovieId + "?reserveDateNull=true";

        String openCinemaRoomId_errorText = "예매하실 상영 관을 선택해 주십시오.";

        String openMovieTitle = openMovieService.openMovieTitle(openMovieId);
        model.addAttribute("openMovieTitle", openMovieTitle);

        Cinema reserveCinema = cinemaService.findCinemaById(openCinemaIdAndDateForm.getOpenCinemaRoomId());
        model.addAttribute("reserveCinema", reserveCinema);

        Map<String, List<OpenCinemaRoomAndSiteDTO>> openCinemaRoomList
                = openMovieService.openCinemaRoomAndSiteList(openMovieId, openCinemaIdAndDateForm.getReserveDate(), openCinemaIdAndDateForm.getOpenCinemaRoomId());
        model.addAttribute("openCinemaRoomList", openCinemaRoomList);

        return "focus/reserve/openRoomAndTime";
    }

    // 여기서부터
    // 좌석 선택 (A-1, A-2, ,,,)
    @PostMapping("/{openMovieId}/openCinema/room")
    public String openCinemaRoomSite(@PathVariable("openMovieId") Long openMovieId,
                                     @ModelAttribute("openCinemaRoomIdForm") OpenCinemaIdAndDateForm openCinemaIdAndDateForm,
                                     @ModelAttribute("reserveForm") ReserveForm reserveForm,
                                     Model model, HttpServletRequest request) {

        OpenMovie openMovie = openMovieService.openMovieById(openMovieId).orElse(null);
        assert openMovie != null; model.addAttribute("openMovie", openMovie);

        List<OpenCinemaRoomSiteSelectDTO> openCinemaRoomSiteList = openMovieService.openCinemaRoomSiteSelect(openCinemaIdAndDateForm.getOpenCinemaRoomId());
        model.addAttribute("openCinemaRoomSiteList", openCinemaRoomSiteList);

        OpenCinemaRoom openCinemaRoom = openMovieService.openCinemaRoomById(openCinemaIdAndDateForm.getOpenCinemaRoomId());
        model.addAttribute("openCinemaRoom", openCinemaRoom);

        String loginMemberId = (String) request.getAttribute(SessionConst.LOGIN_MEMBER_ID);
        String memberName = memberService.findMemberName(loginMemberId);
        model.addAttribute("memberName", memberName);

        return "/ " + openMovieId +  "/openCinema/room/site";

    }

    @PostMapping("/{openMovieId}/openCinema/room/site")
    public String openCinemaRoomSiteReserve(@PathVariable("openMovieId") Long openMovieId,
                                     @ModelAttribute("reserveForm") ReserveForm reserveForm,
                                     Model model, HttpServletRequest request) {

        OpenMovie openMovie = openMovieService.openMovieById(openMovieId).orElse(null);
        assert openMovie != null;

        OpenCinemaRoom openCinemaRoom = openMovieService.openCinemaRoomById(reserveForm.getOpenCinemaRoomId());
        List<CinemaRoomSite> cinemaRoomSiteList = openMovieService.cinemaRoomSiteList(reserveForm.getCinemaRoomSiteIdList());

        // 예매하고자 하는 좌석이 한 번 더 그 사이 예매되었는지 확인하는 코드 추가

        String loginMemberId = (String) request.getAttribute(SessionConst.LOGIN_MEMBER_ID);
        Member member = memberService.findMemberById(loginMemberId);

        String memberName = movieReserveService.movieReserve(member, openCinemaRoom, cinemaRoomSiteList);
        model.addAttribute("memberName", memberName);

        return "";
    }

}