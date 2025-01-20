package pcm.open_movie.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pcm.open_movie.domain.dto.*;
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
import java.util.stream.Collectors;

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
    public List<OpenCinemaDateDTO> openCinemaDateList(Long openMovieId) {
        List<Object[]> openCinemaList = openCinemaRoomRepository.findOpenCinemaDateByOpenMovieId(openMovieId);
        return openCinemaList.stream()
                    .map(row -> new OpenCinemaDateDTO((Long) row[0], (String) row[1], (String) row[2], (String) row[3]))
                    .collect(Collectors.toList());
    }

    @Override
    public List<OpenCinemaDTO> openCinemaList(Long openMovieId) {
        return openCinemaRoomRepository.findOpenCinemaByOpenMovieId(openMovieId);
    }

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
    public Map<Long, List<OpenCinemaRoomDateTimeDTO>> findOpenCinemaRoomList(Long openMovieId, String openMovieDate, Long cinemaId) {

        String[] dateSplit = openMovieDate.split("-");
        int year = Integer.parseInt(dateSplit[0]);
        int month = Integer.parseInt(dateSplit[1]);
        int day = Integer.parseInt(dateSplit[2]);

        LocalDateTime morning = LocalDateTime.of(year, month, day, 11, 0, 0); // 아침
        LocalDateTime lunch = LocalDateTime.of(year, month, day, 16, 0, 0); // 점심
        LocalDateTime dinner = LocalDateTime.of(year, month, day, 21, 0, 0); // 저녁
        LocalDateTime dawn = LocalDateTime.of(year, month, day, 23, 59, 59); // 새벽

        List<OpenCinemaRoomDateTimeDTO> openCinemaRoomList
                = openCinemaRoomRepository.findOpenCinemaRoomList(openMovieId, openMovieDate, cinemaId);

        Map<Long, List<OpenCinemaRoomDateTimeDTO>> result = new ConcurrentHashMap<>();
        List<OpenCinemaRoomDateTimeDTO> morningDTO = new ArrayList<>();
        List<OpenCinemaRoomDateTimeDTO> lunchDTO = new ArrayList<>();
        List<OpenCinemaRoomDateTimeDTO> dinnerDTO = new ArrayList<>();
        List<OpenCinemaRoomDateTimeDTO> dawnDTO = new ArrayList<>();

        for (OpenCinemaRoomDateTimeDTO openCinemaRoomDateTimeDTO : openCinemaRoomList) {
            if(morning.isAfter(openCinemaRoomDateTimeDTO.getOpenCinemaRoomStartDateTime())) morningDTO.add(openCinemaRoomDateTimeDTO);
            else if(lunch.isAfter(openCinemaRoomDateTimeDTO.getOpenCinemaRoomStartDateTime())) lunchDTO.add(openCinemaRoomDateTimeDTO);
            else if(dinner.isAfter(openCinemaRoomDateTimeDTO.getOpenCinemaRoomStartDateTime())) dinnerDTO.add(openCinemaRoomDateTimeDTO);
            else if (dawn.isAfter(openCinemaRoomDateTimeDTO.getOpenCinemaRoomStartDateTime())) dawnDTO.add(openCinemaRoomDateTimeDTO);
        }

        for (OpenCinemaRoomDateTimeDTO openCinemaRoomDateTimeDTO : openCinemaRoomList) {
            LocalDateTime openCinemaRoomStartDateTime = openCinemaRoomDateTimeDTO.getOpenCinemaRoomStartDateTime();
            openCinemaRoomDateTimeDTO.setOpenCinemaRoomStartDateTimeText
                    (openCinemaRoomStartDateTime.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 | HH시 mm분")));
        }

        result.put(1L, morningDTO); // 아침
        result.put(2L, lunchDTO); // 점심
        result.put(3L, dinnerDTO); // 저녁
        result.put(4L, dawnDTO); // 새벽

        return result;
    }

    @Override
    public Map<Long, List<OpenCinemaRoomSiteStatusDTO>> findOpenCinemaRoomSiteStatusList(List<Long> openCinemaRoomIdList) {

        List<OpenCinemaRoomSiteStatusDTO> openCinemaRoomSiteStatusList
                = openCinemaRoomRepository.findOpenCinemaRoomSiteStatusList(openCinemaRoomIdList);

        Map<Long, List<OpenCinemaRoomSiteStatusDTO>> result = new ConcurrentHashMap<>();

        for (Long openCinemaRoomId : openCinemaRoomIdList) {

            List<OpenCinemaRoomSiteStatusDTO> openCinemaRoomSiteStatusListSet = new ArrayList<>();

            for (OpenCinemaRoomSiteStatusDTO openCinemaRoomSiteStatusDTO : openCinemaRoomSiteStatusList) {
                if(openCinemaRoomSiteStatusDTO.getOpenCinemaRoomId().equals(openCinemaRoomId))
                    openCinemaRoomSiteStatusListSet.add(openCinemaRoomSiteStatusDTO);
            }
            result.put(openCinemaRoomId, openCinemaRoomSiteStatusListSet);
        }

        return result;
    }

    @Override
    public List<ReserveOpenCinemaRoomDTO> openCinemaRoomSiteList(Long openCinemaRoomId) {
        return openCinemaRoomRepository.findReserveOpenCinemaRoomList(openCinemaRoomId);
    }

    @Override
    public OpenCinemaRoomAndOpenMovieDTO openCinemaRoomAndOpenMovie(Long openCinemaRoomId) {
        return openCinemaRoomRepository.findOpenCinemaRoomAndOpenMovie(openCinemaRoomId);
    }
}
