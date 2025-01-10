package pcm.open_movie.repository.open;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pcm.open_movie.domain.dto.*;
import pcm.open_movie.domain.entity.Cinema;
import pcm.open_movie.domain.entity.OpenCinemaRoom;

import java.util.List;

@Repository
public interface OpenCinemaRoomRepository extends JpaRepository<OpenCinemaRoom, Long> {

    /*
    @Query("SELECT DISTINCT NEW pcm.open_movie.domain.dto.OpenCinemaDTO(ocr.openMovieDate, c.cinemaName, c.cinemaAddress) " +
            "FROM OpenCinemaRoom ocr " +
            "JOIN FETCH CinemaRoom cr ON ocr.cinemaRoom.cinemaRoomId = cr.cinemaRoomId " +
            "JOIN FETCH Cinema c ON cr.cinema.cinemaId = c.cinemaId " +
            "WHERE ocr.openMovie.openMovieId = :openMovieId AND TO_CHAR(ocr.openMovieDate, 'yyyy-MM-dd') IN :openMovieDateList")
    List<OpenCinemaDTO> findByOpenCinemaList(@Param("openMovieId") Long openMovieId, @Param("openMovieDateList") List<String> openMovieDateList);
     */

    @Query("SELECT c FROM Cinema c JOIN CinemaRoom cr ON c.cinemaId = cr.cinema.cinemaId " +
            "JOIN OpenCinemaRoom ocr ON cr.cinemaRoomId = ocr.cinemaRoom.cinemaRoomId AND ocr.openMovie.openMovieId = :openMovieId")
    List<Cinema> findCinemaByOpenMovieId(@Param("openMovieId") Long openMovieId);

    @Query("SELECT " +
            "TO_CHAR(ocr.openMovieDate, 'yyyy-MM-dd') " +
            "FROM OpenCinemaRoom ocr JOIN FETCH CinemaRoom cr ON ocr.cinemaRoom.cinemaRoomId = cr.cinemaRoomId " +
            "AND cr.cinema.cinemaId IN :openCinemaId " +
            "WHERE ocr.openMovie.openMovieId = :openMovieId " +
            "GROUP BY cr.cinema.cinemaId, TO_CHAR(ocr.openMovieDate, 'yyyy-MM-dd')")
    List<String> findOpenCinemaRoomCountByOpenCinemaIdList(@Param("openMovieId") Long openMovieId, @Param("openCinemaId") Long openCinemaId);

    @Query("SELECT NEW pcm.open_movie.domain.dto.OpenCinemaRoomDTO" +
            "(ocr.openMovieDate, cr.cinemaRoomName, ocr.openCinemaRoomId) " +
            "FROM OpenCinemaRoom ocr JOIN FETCH CinemaRoom cr ON ocr.cinemaRoom.cinemaRoomId = cr.cinemaRoomId " +
            "WHERE TO_CHAR(ocr.openMovieDate, 'yyyy-MM-dd') = :openMovieDate AND ocr.openMovie.openMovieId = :openMovieId")
    List<OpenCinemaRoomDTO> findByOpenCinemaRoomList(@Param("openMovieDate") String openMovieDate, @Param("openMovieId") Long openMovieId);

    @Query("SELECT NEW pcm.open_movie.domain.dto.OpenCinemaRoomAndSiteDTO" +
            "(ocr.openCinemaRoomId, cr.cinemaRoomName, ocr.openMovieDate, crs.cinemaRoomSiteName, mr.cinemaRoomSite.cinemaRoomSiteName) " +
            "FROM CinemaRoom cr " +
            "JOIN FETCH OpenCinemaRoom ocr ON cr.cinemaRoomId = ocr.cinemaRoom.cinemaRoomId AND TO_CHAR(ocr.openMovieDate, 'yyyy-MM-dd') = :openMovieDate " +
            "JOIN FETCH OpenMovie om ON om.openMovieId = ocr.openMovie.openMovieId AND om.openMovieId = :openMovieId " +
            "JOIN FETCH CinemaRoomSite crs ON crs.cinemaRoom.cinemaRoomId = ocr.cinemaRoom.cinemaRoomId " +
            "JOIN FETCH MovieReserve mr ON mr.cinemaRoomSite.CinemaRoomSiteId = crs.CinemaRoomSiteId " +
            "WHERE cr.cinema.cinemaId = :cinemaId")
    List<OpenCinemaRoomAndSiteDTO> findOpenCinemaRoomAndSite
            (@Param("openMovieId") Long openMovieId, @Param("openMovieDate") String openMovieDate, @Param("cinemaId") Long cinemaId);

    //
    @Query("SELECT NEW pcm.open_movie.domain.dto.OpenCinemaRoomSiteDTO(ocr.openCinemaRoomId, crs.cinemaRoomSiteName, mr.movieReserveId) " +
            "FROM OpenCinemaRoom ocr LEFT JOIN FETCH MovieReserve mr " +
            "ON ocr.openCinemaRoomId = mr.openCinemaRoom.openCinemaRoomId " +
            "LEFT JOIN FETCH CinemaRoomSite crs " +
            "ON ocr.cinemaRoom.cinemaRoomId = crs.cinemaRoom.cinemaRoomId " +
            "WHERE ocr.openCinemaRoomId IN :openCinemaRoomIdList")
    List<OpenCinemaRoomSiteDTO> findOpenCinemaRoomSiteList(@Param("openCinemaRoomIdList") List<Long> openCinemaRoomIdList);

    @Query("SELECT NEW pcm.open_movie.domain.dto.OpenCinemaRoomSiteSelectDTO(crs.cinemaRoomSiteName, mr.movieReserveId) " +
            "FROM OpenCinemaRoom ocr LEFT JOIN FETCH MovieReserve mr " +
            "ON ocr.openCinemaRoomId = mr.openCinemaRoom.openCinemaRoomId " +
            "LEFT JOIN FETCH CinemaRoomSite crs " +
            "ON ocr.cinemaRoom.cinemaRoomId = crs.cinemaRoom.cinemaRoomId " +
            "WHERE ocr.openCinemaRoomId = :openCinemaRoomId")
    List<OpenCinemaRoomSiteSelectDTO> findCinemaRoomSiteListByOpenCinemaRoomId(@Param("openCinemaRoomId") Long openCinemaRoomId);

    @Query("SELECT crs.CinemaRoomSiteId FROM OpenCinemaRoom ocr " +
            "JOIN FETCH MovieReserve mr ON ocr.openCinemaRoomId = mr.openCinemaRoom.openCinemaRoomId " +
            "JOIN FETCH CinemaRoomSite crs ON mr.cinemaRoomSite.CinemaRoomSiteId = crs.CinemaRoomSiteId AND crs.CinemaRoomSiteId IN :cinemaRoomSiteIdList")
    boolean findSiteReserveTF(@Param("cinemaRoomSiteIdList") List<Long> cinemaRoomSiteIdList);

    @Query("SELECT ocr FROM OpenCinemaRoom ocr WHERE ocr.openCinemaRoomId = :openCinemaRoomId")
    OpenCinemaRoom findOpenCinemaRoomById(@Param("openCinemaRoomId") Long openCinemaRoomId);

}
