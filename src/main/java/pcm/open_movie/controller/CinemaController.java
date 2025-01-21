package pcm.open_movie.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pcm.open_movie.domain.dto.CinemaRoomIdAndNameDTO;
import pcm.open_movie.domain.entity.Cinema;
import pcm.open_movie.service.CinemaService;
import pcm.open_movie.service.MemberService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/focus/findCinema")
public class CinemaController {

    private final CinemaService cinemaService;

    @GetMapping
    public String cinemaList(Model model) {

        List<Cinema> cinemaList = cinemaService.cinemaList();
        Long findCinemaId = 0L;

        model.addAttribute("cinemaList", cinemaList);
        model.addAttribute("findCinemaId", findCinemaId);

        return "focus/findCinema/cinemaSearch";
    }

    // cinema
    // 관 정보, 좌석 정보 1 : N
    @PostMapping
    public String cinemaRoomAndSiteList(@RequestParam("findCinemaId") Long findCinemaId, Model model) {

        List<Cinema> cinemaList = cinemaService.cinemaList();
        model.addAttribute("cinemaList", cinemaList);

        Cinema findCinema = cinemaService.findCinemaById(findCinemaId);
        Map<Long, List<String>> cinemaRoomAndSiteList = cinemaService.cinemaRoomAndSiteList(findCinemaId);

        List<Long> cinemaRoomIdList = new ArrayList<>(cinemaRoomAndSiteList.keySet());

        List<CinemaRoomIdAndNameDTO> cinemaRoomIdAndNameList = cinemaService.cinemaRoomNameList(cinemaRoomIdList);
        model.addAttribute("cinemaRoomIdAndNameList", cinemaRoomIdAndNameList);

        // 1 - 1관, 2 - 2관
        model.addAttribute("findCinema", findCinema);
        model.addAttribute("findCinemaId", findCinemaId);
        model.addAttribute("cinemaRoomAndSiteList", cinemaRoomAndSiteList);

        return "focus/findCinema/cinemaSearch";
    }

}
