package pcm.open_movie.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pcm.open_movie.config.SessionConst;
import pcm.open_movie.domain.dto.*;
import pcm.open_movie.domain.entity.*;
import pcm.open_movie.service.CinemaService;
import pcm.open_movie.service.MemberService;
import pcm.open_movie.service.MovieReserveService;
import pcm.open_movie.service.OpenMovieService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
    public String focus(Model model) {

        List<OpenMovie> openMovieList = openMovieService.openMovieList();
        model.addAttribute("openMovieList", openMovieList);

       return "focus/main";
    }

    @GetMapping("/{openMovieId}")
    public String openCinema(@PathVariable("openMovieId") Long openMovieId,
                             @RequestParam(value = "openCinemaRoomIdNull", required = false) String openCinemaRoomIdNull,
                             @RequestParam(value = "reserveDateNull", required = false) String reserveDateNull,
                             Model model) {

        if(openCinemaRoomIdNull != null) {
            String openCinemaRoomIdNull_errorText = "관람할 영화관을 선택해 주십시오.";
            model.addAttribute("openCinemaRoomIdNull_errorText", openCinemaRoomIdNull_errorText);
        } else if(reserveDateNull != null) {
            String reserveDateNull_errorText = "관람할 날짜를 선택해 주십시오.";
            model.addAttribute("reserveDateNull_errorText", reserveDateNull_errorText);
        }

        // 선택한 상영 영화
        OpenMovie openMovie = openMovieService.openMovieById(openMovieId).orElse(null);
        model.addAttribute("openMovie", openMovie);

        // 오픈 날짜, 상영관
        List<OpenCinemaDTO> openCinemaList = openMovieService.openCinemaList(openMovieId);
        model.addAttribute("openCinemaList", openCinemaList);

        Map<Long, List<OpenCinemaDateDTO>> openCinemaAndDateList = openMovieService.openCinemaDateList(openMovieId, openCinemaList);
        model.addAttribute("openCinemaAndDateList", openCinemaAndDateList);

        return "focus/reserve/openCinema";

    }

    // 상영 시간, 상영 관 선택 (1관 17:30)
    @GetMapping("/{openMovieId}/cinema")
    public String openCinemaRoomList(@PathVariable("openMovieId") Long openMovieId,
                                     @RequestParam(value = "cinemaId", required = false) Long cinemaId,
                                     @RequestParam(value = "reserveDate", required = false) String reserveDate,
                                     @RequestParam(value = "openCinemaRoomIdNull", required = false) String openCinemaRoomIdNull,
                                     Model model) {

        if(cinemaId == null && openCinemaRoomIdNull == null) return "redirect:/focus/" + openMovieId + "?openCinemaRoomIdNull=true";
        if(reserveDate == null && openCinemaRoomIdNull == null) return "redirect:/focus/" + openMovieId + "?reserveDateNull=true";

        if(cinemaId != null && reserveDate != null && openCinemaRoomIdNull != null) {
            String openCinemaRoomId_errorText = "예매하실 상영 관을 선택해 주십시오.";
            model.addAttribute("openCinemaRoomId_errorText", openCinemaRoomId_errorText);
        }

        model.addAttribute("cinemaId", cinemaId);
        model.addAttribute("reserveDate", reserveDate);

        String openMovieTitle = openMovieService.openMovieTitle(openMovieId);
        model.addAttribute("openMovieTitle", openMovieTitle);

        Cinema reserveCinema = cinemaService.getCinema(cinemaId);
        model.addAttribute("reserveCinema", reserveCinema);

        Map<Long, List<OpenCinemaRoomDateTimeDTO>> openCinemaRoomList
                = openMovieService.openCinemaRoomList(openMovieId, reserveDate, cinemaId);

        Map<Long, List<OpenCinemaRoomSiteStatusDTO>> openCinemaRoomSiteStatusList
                = openMovieService.openCinemaRoomSiteStatusList(openCinemaRoomList);

        model.addAttribute("openCinemaRoomSiteStatusList", openCinemaRoomSiteStatusList);
        model.addAttribute("openCinemaRoomList", openCinemaRoomList);

        // *
        Map<Long, String> dateTimeTextList = new ConcurrentHashMap<>();
        dateTimeTextList.put(1L, "아침");
        dateTimeTextList.put(2L, "점심");
        dateTimeTextList.put(3L, "저녁");
        dateTimeTextList.put(4L, "새벽");
        model.addAttribute("dateTimeTextList", dateTimeTextList);

        return "focus/reserve/openRoomAndTime";
    }

    @GetMapping("/{openMovieId}/cinemaRoom")
    public String openCinemaRoomSite(@PathVariable("openMovieId") Long openMovieId,
                                     @RequestParam(value = "cinemaId", required = false) Long cinemaId,
                                     @RequestParam(value = "reserveDate", required = false) String reserveDate,
                                     @RequestParam(value = "openCinemaRoomId", required = false) Long openCinemaRoomId,
                                     @RequestParam(value = "cinemaRoomSiteIdNull", required = false) String cinemaRoomSiteIdNull,
                                     Model model) {

        if(openCinemaRoomId == null && cinemaId != null && reserveDate != null)
            return "redirect:/focus/" + openMovieId + "/cinema?cinemaId="
                    + cinemaId + "&reserveDate=" + reserveDate + "&openCinemaRoomIdNull=true";

        if(cinemaRoomSiteIdNull != null && cinemaRoomSiteIdNull.equals("true")) {
            String cinemaRoomSiteIdNull_errorText = "좌석을 선택해 주십시오.";
            model.addAttribute("cinemaRoomSiteIdNull_errorText", cinemaRoomSiteIdNull_errorText);
        }

        model.addAttribute("openCinemaRoomId", openCinemaRoomId);

        // rollback
        model.addAttribute("cinemaId", cinemaId);
        model.addAttribute("reserveDate", reserveDate);

        OpenCinemaRoomAndOpenMovieDTO openCinemaRoomAndOpenMovieDTO = openMovieService.openCinemaRoomAndOpenMovie(openCinemaRoomId);
        model.addAttribute("openCinemaRoomAndOpenMovieDTO", openCinemaRoomAndOpenMovieDTO);

        List<ReserveOpenCinemaRoomDTO> openCinemaRoomSiteList = openMovieService.openCinemaRoomSiteList(openCinemaRoomId);
        model.addAttribute("openCinemaRoomSiteList", openCinemaRoomSiteList);

        return "focus/reserve/openRoomSiteList";

    }

    @PostMapping("/{openMovieId}/cinemaRoomSite")
    public String openCinemaRoomSiteReserve(@PathVariable("openMovieId") Long openMovieId,
                                            @RequestParam(value = "openCinemaRoomId", required = false) Long openCinemaRoomId,
                                            @RequestParam(value = "cinemaId", required = false) Long cinemaId,
                                            @RequestParam(value = "reserveDate", required = false) String reserveDate,
                                            @RequestParam(value = "cinemaRoomSiteIdList", required = false) List<Long> cinemaRoomSiteIdList,
                                            HttpServletRequest request) {

        if (cinemaRoomSiteIdList == null) return "redirect:/focus/" + openMovieId
                + "/cinemaRoom?openCinemaRoomId=" + openCinemaRoomId + "&cinemaId=" + cinemaId + "&reserveDate=" + reserveDate + "&cinemaRoomSiteIdNull=true";

        OpenCinemaRoom openCinemaRoom = openMovieService.openCinemaRoomById(openCinemaRoomId);
        List<CinemaRoomSite> cinemaRoomSiteList = openMovieService.cinemaRoomSiteList(cinemaRoomSiteIdList);

        HttpSession session = request.getSession(false);
        String loginMemberId = (String) session.getAttribute(SessionConst.LOGIN_MEMBER_ID);

        Member member = memberService.getMember(loginMemberId);
        movieReserveService.movieReserve(member, openCinemaRoom, cinemaRoomSiteList);

        return "redirect:/focus";
    }

}