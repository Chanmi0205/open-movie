package pcm.open_movie.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.internal.constraintvalidators.bv.money.MaxValidatorForMonetaryAmount;
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

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

       return "focus/main";
    }

    // 상영일, 영화관 선택 (PCM 2024-10-05)
    @GetMapping("/{openMovieId}")
    public String openCinema(@PathVariable("openMovieId") Long openMovieId,
                             @RequestParam(value = "openCinemaRoomIdNull", required = false) String openCinemaRoomIdNull,
                             @RequestParam(value = "reserveDateNull", required = false) String reserveDateNull,
                             Model model, HttpServletRequest request) {

        if(openCinemaRoomIdNull != null) {
            String openCinemaRoomIdNull_errorText = "관람할 영화관을 선택해 주십시오.";
            model.addAttribute("openCinemaRoomIdNull_errorText", openCinemaRoomIdNull_errorText);
        } else if(reserveDateNull != null) {
            String reserveDateNull_errorText = "관람할 날짜를 선택해 주십시오.";
            model.addAttribute("reserveDateNull_errorText", reserveDateNull_errorText);
        }

        Map<Long, List<OpenCinemaDateDTO>> openCinemaAndDateList = new ConcurrentHashMap<>();

        // 선택한 상영 영화
        OpenMovie openMovie = openMovieService.openMovieById(openMovieId).orElse(null);
        model.addAttribute("openMovie", openMovie);

        // ~
        // 오픈 날짜, 상영관
        List<OpenCinemaDTO> openCinemaList = openMovieService.openCinemaList(openMovieId);
        model.addAttribute("openCinemaList", openCinemaList);

        List<OpenCinemaDateDTO> openCinemaDateList = openMovieService.openCinemaDateList(openMovieId);

        for (OpenCinemaDTO openCinemaDTO : openCinemaList) {

            Long openCinemaId = openCinemaDTO.getCinemaId();

            List<OpenCinemaDateDTO> openCinemaDTOList = new ArrayList<>();

            for (OpenCinemaDateDTO openCinemaDateDTO : openCinemaDateList) {
                if (openCinemaId.equals(openCinemaDateDTO.getCinemaId())) {
                    openCinemaDTOList.add(openCinemaDateDTO);
                }

                openCinemaAndDateList.put(openCinemaId, openCinemaDTOList);
            }
        }

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

        Cinema reserveCinema = cinemaService.findCinemaById(cinemaId);
        model.addAttribute("reserveCinema", reserveCinema);


        Map<Long, List<OpenCinemaRoomDateTimeDTO>> openCinemaRoomList
                = openMovieService.findOpenCinemaRoomList(openMovieId, reserveDate, cinemaId);

        ArrayList<Long> openCinemaRoomIdList = new ArrayList<>();

        for (List<OpenCinemaRoomDateTimeDTO> openCinemaRoomDateTimeDTOList : openCinemaRoomList.values()) {
            for (OpenCinemaRoomDateTimeDTO openCinemaRoomDateTimeDTO : openCinemaRoomDateTimeDTOList) {
                openCinemaRoomIdList.add(openCinemaRoomDateTimeDTO.getOpenCinemaRoomId());
            }
        }

        Map<Long, List<OpenCinemaRoomSiteStatusDTO>> openCinemaRoomSiteStatusList
                = openMovieService.findOpenCinemaRoomSiteStatusList(openCinemaRoomIdList);

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

    // 여기서부터
    // 좌석 선택 (A-1, A-2, ,,,)
    @GetMapping("/{openMovieId}/cinemaRoom")
    public String openCinemaRoomSite(@PathVariable("openMovieId") Long openMovieId,
                                     @RequestParam(value = "cinemaId", required = false) Long cinemaId,
                                     @RequestParam(value = "reserveDate", required = false) String reserveDate,
                                     @RequestParam(value = "openCinemaRoomId", required = false) Long openCinemaRoomId,
                                     @RequestParam(value = "cinemaRoomSiteIdNull", required = false) String cinemaRoomSiteIdNull,
                                     Model model, HttpServletRequest request) {

        if(openCinemaRoomId == null && cinemaId != null && reserveDate != null)
            return "redirect:/focus/" + openMovieId + "/cinema?cinemaId="
                    + cinemaId + "&reserveDate=" + reserveDate + "&openCinemaRoomIdNull=true";

        if(cinemaRoomSiteIdNull != null) {
            String cinemaRoomSiteIdNull_errorText = "좌석을 선택해 주십시오.";
            model.addAttribute("cinemaRoomSiteIdNull_errorText", cinemaRoomSiteIdNull_errorText);
        }

        model.addAttribute("openCinemaRoomId", openCinemaRoomId);

        OpenCinemaRoomAndOpenMovieDTO openCinemaRoomAndOpenMovieDTO = openMovieService.openCinemaRoomAndOpenMovie(openCinemaRoomId);
        model.addAttribute("openCinemaRoomAndOpenMovieDTO", openCinemaRoomAndOpenMovieDTO);

        List<ReserveOpenCinemaRoomDTO> openCinemaRoomSiteList = openMovieService.openCinemaRoomSiteList(openCinemaRoomId);
        model.addAttribute("openCinemaRoomSiteList", openCinemaRoomSiteList);

        return "focus/reserve/openRoomSiteList";

    }

    @PostMapping("/{openMovieId}/cinemaRoomSite")
    public String openCinemaRoomSiteReserve(@PathVariable("openMovieId") Long openMovieId,
                                            @RequestParam(value = "openCinemaRoomId", required = false) Long openCinemaRoomId,
                                            @RequestParam(value = "cinemaRoomSiteIdList", required = false) List<Long> cinemaRoomSiteIdList,
                                            Model model, HttpServletRequest request) {

        if (cinemaRoomSiteIdList == null) return "redirect:/focus/" + openMovieId
                + "/cinemaRoom?openCinemaRoomId=" + openCinemaRoomId + "&cinemaRoomSiteIdNull=true";

        OpenCinemaRoom openCinemaRoom = openMovieService.openCinemaRoomById(openCinemaRoomId);
        List<CinemaRoomSite> cinemaRoomSiteList = openMovieService.cinemaRoomSiteList(cinemaRoomSiteIdList);

        HttpSession session = request.getSession(false);
        String loginMemberId = (String) session.getAttribute(SessionConst.LOGIN_MEMBER_ID);

        Member member = memberService.findMemberById(loginMemberId);
        movieReserveService.movieReserve(member, openCinemaRoom, cinemaRoomSiteList);

        return "redirect:/focus";
    }

}