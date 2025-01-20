package pcm.open_movie.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pcm.open_movie.domain.dto.MovieReserveSiteDTO;
import pcm.open_movie.domain.entity.MovieReserve;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MovieReserveRepository extends JpaRepository<MovieReserve, Long>, MovieReserveRepositoryCustom {

    @Query("SELECT NEW pcm.open_movie.domain.dto.MovieReserveSiteDTO" +
            "(ocr.openCinemaRoomId, crs.cinemaRoomSiteName) " +
            "FROM MovieReserve mr " +
            "LEFT JOIN FETCH OpenCinemaRoom ocr " +
            "ON mr.openCinemaRoom.openCinemaRoomId = ocr.openCinemaRoomId AND ocr.openCinemaRoomId IN :openCinemaRoomIdList " +
            "LEFT JOIN FETCH CinemaRoomSite crs ON mr.cinemaRoomSite.cinemaRoomSiteId = crs.cinemaRoomSiteId " +
            "WHERE mr.member.memberId = :memberId")
    List<MovieReserveSiteDTO> findMovieReserveSiteList
            (@Param("memberId") String memberId, @Param("openCinemaRoomIdList") List<Long> openCinemaRoomIdList);

}
