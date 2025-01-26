package pcm.open_movie.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pcm.open_movie.domain.dto.cinema.CinemaRoomIdAndNameDTO;
import pcm.open_movie.domain.entity.Cinema;
import pcm.open_movie.service.CinemaService;

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
        model.addAttribute("cinemaList", cinemaList);

        return "focus/findCinema/cinemaSearch";
    }

    // cinema
    // 관 정보, 좌석 정보 1 : N
    @PostMapping
    public String cinemaRoomAndSiteList(@RequestParam("findCinemaId") Long findCinemaId, Model model) {

        List<Cinema> cinemaList = cinemaService.cinemaList();
        model.addAttribute("cinemaList", cinemaList);

        Cinema findCinema = cinemaService.getCinema(findCinemaId);
        List<CinemaRoomIdAndNameDTO> cinemaRoomList = cinemaService.cinemaRoomList(findCinemaId);
        Map<Long, List<String>> cinemaRoomSiteList = cinemaService.cinemaRoomSiteList(cinemaRoomList);

        model.addAttribute("findCinema", findCinema);
        model.addAttribute("cinemaRoomList", cinemaRoomList);
        model.addAttribute("cinemaRoomSiteList", cinemaRoomSiteList);

        return "focus/findCinema/cinemaSearch";
    }

}
