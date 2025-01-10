package pcm.open_movie.repository.cinema;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pcm.open_movie.domain.dto.CinemaRoomIdAndNameDTO;
import pcm.open_movie.domain.entity.CinemaRoom;
import pcm.open_movie.domain.entity.CinemaRoomSite;

import java.util.List;

@Repository
public interface CinemaRoomRepository extends JpaRepository<CinemaRoom, Long> {

    @Query("SELECT cr FROM CinemaRoom cr WHERE cr.cinema.cinemaId = :cinemaId")
    List<CinemaRoom> findCinemaRoomByCinemaId(@Param("cinemaId") Long cinemaId);

    @Query("SELECT crs FROM CinemaRoomSite crs JOIN FETCH CinemaRoom cr ON cr.cinema.cinemaId = :cinemaId")
    List<CinemaRoomSite> findCinemaRoomAndSiteByCinemaId(@Param("cinemaId") Long cinemaId);

    @Query("SELECT NEW pcm.open_movie.domain.dto.CinemaRoomIdAndNameDTO(cr.cinemaRoomId, cr.cinemaRoomName) " +
            "FROM CinemaRoom cr WHERE cr.cinemaRoomId IN :cinemaRoomIdList")
    List<CinemaRoomIdAndNameDTO> findCinemaRoomIdAndNameDTOByCinemaRoomIdList(@Param("cinemaRoomIdList") List<Long> cinemaRoomIdList);

}
