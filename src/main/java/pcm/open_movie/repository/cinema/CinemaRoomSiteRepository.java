package pcm.open_movie.repository.cinema;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pcm.open_movie.domain.entity.CinemaRoomSite;

import java.util.List;

@Repository
public interface CinemaRoomSiteRepository extends JpaRepository<CinemaRoomSite, Long> {

    @Query("SELECT crs FROM CinemaRoomSite crs WHERE crs.cinemaRoomSiteId IN :cinemaRoomSiteIdList")
    List<CinemaRoomSite> findCinemaRoomSiteByIdList(@Param("cinemaRoomSiteIdList") List<Long> cinemaRoomSiteIdList);

}
