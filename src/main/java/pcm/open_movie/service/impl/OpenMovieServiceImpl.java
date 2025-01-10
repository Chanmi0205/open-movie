package pcm.open_movie.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pcm.open_movie.domain.dto.*;
import pcm.open_movie.domain.entity.Cinema;
import pcm.open_movie.domain.entity.CinemaRoomSite;
import pcm.open_movie.domain.entity.OpenCinemaRoom;
import pcm.open_movie.domain.entity.OpenMovie;
import pcm.open_movie.repository.cinema.CinemaRoomSiteRepository;
import pcm.open_movie.repository.open.OpenCinemaRoomRepository;
import pcm.open_movie.repository.open.OpenMovieRepository;
import pcm.open_movie.service.OpenMovieService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Transactional
public class OpenMovieServiceImpl implements OpenMovieService {

    private final OpenMovieRepository openMovieRepository;
    private final OpenCinemaRoomRepository openCinemaRoomRepository;
    private final CinemaRoomSiteRepository cinemaRoomSiteRepository;

    @Override
    public String openMovieTitle(Long openMovieId) {
        return openMovieRepository.findTitleByOpenMovieId(openMovieId);
    }

    @Override
    public List<OpenMovie> openMovieList() {
        return openMovieRepository.findAll();
    }

    @Override
    public Optional<OpenMovie> openMovieById(Long openMovieId) {
        return openMovieRepository.findById(openMovieId);
    }

    @Override
    public List<Cinema> openCinemaList(Long openMovieId) {
        return openCinemaRoomRepository.findCinemaByOpenMovieId(openMovieId);
    }

    @Override
    public List<String> openCinemaRoomCount(Long openMovieId, Long openCinemaRoomId) {
        return openCinemaRoomRepository.findOpenCinemaRoomCountByOpenCinemaIdList(openMovieId, openCinemaRoomId);
    }

    /*
    @Override
    public Map<LocalDateTime, List<OpenCinemaDTO>> openCinemaList(Long openMovieId) {

        Map<LocalDateTime, List<OpenCinemaDTO>> openCinemaList = new ConcurrentHashMap<>();

        List<String> openDateList = new ArrayList<>();
        String day1 = LocalDateTime.now().toLocalDate().toString();
        String day2 = LocalDateTime.now().plusDays(1).toLocalDate().toString();
        String day3 = LocalDateTime.now().plusDays(2).toLocalDate().toString();
        String day4 = LocalDateTime.now().plusDays(3).toLocalDate().toString();
        openDateList.add(day1);
        openDateList.add(day2);
        openDateList.add(day3);
        openDateList.add(day4);

        List<OpenCinemaDTO> cinemaNameAndCinemaAddressList = openCinemaRoomRepository.findByOpenCinemaList(openMovieId, openDateList);

        int day = 0;

        for (String dateTime : openDateList) {

            List<OpenCinemaDTO> DateBulk = new ArrayList<>();
            for (OpenCinemaDTO openCinemaDTO : cinemaNameAndCinemaAddressList) {
                if (dateTime.equals(openCinemaDTO.getCinemaRoomDate().toLocalDate().toString())) {
                    DateBulk.add(openCinemaDTO);
                }
            }
            openCinemaList.put(LocalDateTime.now().plusDays(day), DateBulk);
            day++;
        }

        return openCinemaList;

    }
     */

    @Override
    public Map<OpenCinemaRoomDTO, List<OpenCinemaRoomSiteDTO>> openCinemaRoomAndSiteList(Long openMovieId, LocalDateTime openCinemaRoomDate) {

        String openCinemaRoomDateFormat = openCinemaRoomDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        // 관PK, 관명
        List<OpenCinemaRoomDTO> openCinemaRoomList = openCinemaRoomRepository.findByOpenCinemaRoomList(openCinemaRoomDateFormat, openMovieId);

        for (OpenCinemaRoomDTO openCinemaRoomDTO : openCinemaRoomList) {
            openCinemaRoomDTO.setOpenCinemaDateFormat(openCinemaRoomDate.format(DateTimeFormatter.ofPattern("yyyy'년' MM'월' dd'일' HH'시' mm'분'")));
        }

        // 관 ID 추출
        List<Long> cinemaRoomIdList = new ArrayList<>();
        for (OpenCinemaRoomDTO openCinemaRoomDTO : openCinemaRoomList) {
            cinemaRoomIdList.add(openCinemaRoomDTO.getOpenCinemaRoomId());
        }

        // 관ID, 관좌석명, 좌석예매여부
        List<OpenCinemaRoomSiteDTO> openCinemaRoomSiteList = openCinemaRoomRepository.findOpenCinemaRoomSiteList(cinemaRoomIdList);

        Map<OpenCinemaRoomDTO, List<OpenCinemaRoomSiteDTO>> openCinemaRoomAndSiteList = new HashMap<>();

        // 관PK - 좌석1(좌석예매여부), 좌석2(좌석예매여부) ...
        for (OpenCinemaRoomDTO openCinemaRoomDTO : openCinemaRoomList) {

            List<OpenCinemaRoomSiteDTO> bulkSiteList = new ArrayList<>();

            for (OpenCinemaRoomSiteDTO openCinemaRoomSiteDTO : openCinemaRoomSiteList) {
                if (openCinemaRoomDTO.getOpenCinemaRoomId().equals(openCinemaRoomSiteDTO.getOpenCinemaRoomId())) {
                    bulkSiteList.add(openCinemaRoomSiteDTO);
                }
            }

            openCinemaRoomAndSiteList.put(openCinemaRoomDTO, bulkSiteList);
        }
        return openCinemaRoomAndSiteList;
    }

    @Override
    public List<OpenCinemaRoomSiteSelectDTO> openCinemaRoomSiteSelect(Long openCinemaRoomId) {
        return openCinemaRoomRepository.findCinemaRoomSiteListByOpenCinemaRoomId(openCinemaRoomId);
    }

    @Override
    public OpenCinemaRoom openCinemaRoomById(Long openCinemaRoomId) {
        return openCinemaRoomRepository.findById(openCinemaRoomId).orElse(null);
    }

    @Override
    public List<CinemaRoomSite> cinemaRoomSiteList(List<Long> cinemaRoomSiteIdList) {
        return cinemaRoomSiteRepository.findCinemaRoomSiteByIdList(cinemaRoomSiteIdList);
    }

    // 참이면 존재하고, 거짓이면 좌석 예매를 하지 않은 상태임
    @Override
    public boolean cinemaRoomSiteReserveCheck(List<Long> cinemaRoomSiteIdList) {
        return openCinemaRoomRepository.findSiteReserveTF(cinemaRoomSiteIdList);
    }

    @Override
    public Map<String, List<OpenCinemaRoomAndSiteDTO>> openCinemaRoomAndSiteList(Long openMovieId, String openMovieDate, Long cinemaId) {

        // 11시 전, 16시 전, 21시 전, 23시 59분 전

        String[] dateSplit = openMovieDate.split("-");
        int year = Integer.parseInt(dateSplit[0]);
        int month = Integer.parseInt(dateSplit[1]);
        int day = Integer.parseInt(dateSplit[2]);

        LocalDateTime morning = LocalDateTime.of(year, month, day, 11, 0, 0); // 아침
        LocalDateTime lunch = LocalDateTime.of(year, month, day, 16, 0, 0); // 점심
        LocalDateTime dinner = LocalDateTime.of(year, month, day, 21, 0, 0); // 저녁
        LocalDateTime dawn = LocalDateTime.of(year, month, day, 23, 59, 59); // 새벽

        List<OpenCinemaRoomAndSiteDTO> openCinemaRoomAndSiteList = openCinemaRoomRepository.findOpenCinemaRoomAndSite(openMovieId, openMovieDate, cinemaId);

        Map<String, List<OpenCinemaRoomAndSiteDTO>> result = new ConcurrentHashMap<>();
        List<OpenCinemaRoomAndSiteDTO> morningDTO = new ArrayList<>();
        List<OpenCinemaRoomAndSiteDTO> lunchDTO = new ArrayList<>();
        List<OpenCinemaRoomAndSiteDTO> dinnerDTO = new ArrayList<>();
        List<OpenCinemaRoomAndSiteDTO> dawnDTO = new ArrayList<>();

        for (OpenCinemaRoomAndSiteDTO openCinemaRoomAndSiteDTO : openCinemaRoomAndSiteList) {
            if(morning.isAfter(openCinemaRoomAndSiteDTO.getOpenCinemaRoomStartDateTime())) morningDTO.add(openCinemaRoomAndSiteDTO);
            else if(lunch.isAfter(openCinemaRoomAndSiteDTO.getOpenCinemaRoomStartDateTime())) lunchDTO.add(openCinemaRoomAndSiteDTO);
            else if(dinner.isAfter(openCinemaRoomAndSiteDTO.getOpenCinemaRoomStartDateTime())) dinnerDTO.add(openCinemaRoomAndSiteDTO);
            else if (dawn.isAfter(openCinemaRoomAndSiteDTO.getOpenCinemaRoomStartDateTime())) dawnDTO.add(openCinemaRoomAndSiteDTO);
        }

        result.put("아침", morningDTO);
        result.put("점심", lunchDTO);
        result.put("저녁", dinnerDTO);
        result.put("새벽", dawnDTO);

        return result;
    }

}
