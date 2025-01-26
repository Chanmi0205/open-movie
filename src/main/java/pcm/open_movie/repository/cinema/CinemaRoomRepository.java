package pcm.open_movie.repository.cinema;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pcm.open_movie.domain.dto.cinema.CinemaRoomIdAndNameDTO;
import pcm.open_movie.domain.dto.cinema.CinemaRoomIdAndSiteNameDTO;
import pcm.open_movie.domain.entity.CinemaRoom;

import java.util.List;

@Repository
public interface CinemaRoomRepository extends JpaRepository<CinemaRoom, Long> {

    @Query("SELECT NEW pcm.open_movie.domain.dto.cinema.CinemaRoomIdAndSiteNameDTO" +
            "(cr.cinemaRoomId, crs.cinemaRoomSiteName) " +
            "FROM CinemaRoomSite crs " +
            "LEFT JOIN FETCH CinemaRoom cr ON crs.cinemaRoom.cinemaRoomId = cr.cinemaRoomId " +
            "WHERE crs.cinemaRoom.cinemaRoomId IN :cinemaRoomIdList")
    List<CinemaRoomIdAndSiteNameDTO> findCinemaRoomSiteNameList(@Param("cinemaRoomIdList") List<Long> cinemaRoomIdList);

    @Query("SELECT NEW pcm.open_movie.domain.dto.cinema.CinemaRoomIdAndNameDTO(cr.cinemaRoomId, cr.cinemaRoomName) " +
            "FROM CinemaRoom cr WHERE cr.cinema.cinemaId =:cinemaId")
    List<CinemaRoomIdAndNameDTO> findCinemaRoomIdAndNameByCinemaRoomId(@Param("cinemaId") Long cinemaId);

}
