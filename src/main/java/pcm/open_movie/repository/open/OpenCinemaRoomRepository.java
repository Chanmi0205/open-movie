package pcm.open_movie.repository.open;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pcm.open_movie.domain.dto.*;
import pcm.open_movie.domain.entity.OpenCinemaRoom;

import java.util.List;

@Repository
public interface OpenCinemaRoomRepository extends JpaRepository<OpenCinemaRoom, Long> {

    @Query("SELECT DISTINCT NEW pcm.open_movie.domain.dto.OpenCinemaDTO" +
            "(c.cinemaId, c.cinemaAddress, c.cinemaName) " +
            "FROM OpenMovie om " +
            "LEFT JOIN FETCH OpenCinemaRoom ocr ON om.openMovieId = ocr.openMovie.openMovieId AND ocr.openMovieDate >= NOW() " +
            "LEFT JOIN FETCH CinemaRoom cr ON ocr.cinemaRoom.cinemaRoomId = cr.cinemaRoomId " +
            "LEFT JOIN FETCH Cinema c ON cr.cinema.cinemaId = c.cinemaId " +
            "WHERE om.openMovieId = :openMovieId")
    List<OpenCinemaDTO> findOpenCinemaByOpenMovieId(@Param("openMovieId") Long openMovieId);

    @Query(value =
            "SELECT c.cinema_Id as cinemaId, c.cinema_Address as cinemaAddress, " +
                    "c.cinema_Name as cinemaName, TO_CHAR(ocr.open_Movie_Date, 'yyyy-MM-dd') as openMovieDateFormat " +
            "FROM Open_Movie om " +
            "LEFT JOIN Open_Cinema_Room ocr ON om.open_Movie_Id = ocr.open_Movie_Id AND ocr.Open_Movie_Date >= NOW() " +
            "LEFT JOIN Cinema_Room cr ON ocr.cinema_Room_Id = cr.cinema_Room_Id " +
            "LEFT JOIN Cinema c ON cr.cinema_Id = c.cinema_Id " +
            "WHERE om.open_Movie_Id = :openMovieId " +
            "GROUP BY cinemaId, cinemaAddress, cinemaName, openMovieDateFormat " +
            "ORDER BY c.cinema_Id", nativeQuery = true)
    List<Object[]> findOpenCinemaDateByOpenMovieId(@Param("openMovieId") Long openMovieId);

    @Query("SELECT NEW pcm.open_movie.domain.dto.OpenCinemaRoomDTO" +
            "(ocr.openMovieDate, cr.cinemaRoomName, ocr.openCinemaRoomId) " +
            "FROM OpenCinemaRoom ocr JOIN FETCH CinemaRoom cr ON ocr.cinemaRoom.cinemaRoomId = cr.cinemaRoomId " +
            "WHERE TO_CHAR(ocr.openMovieDate, 'yyyy-MM-dd') = :openMovieDate AND ocr.openMovie.openMovieId = :openMovieId")
    List<OpenCinemaRoomDTO> findByOpenCinemaRoomList(@Param("openMovieDate") String openMovieDate, @Param("openMovieId") Long openMovieId);

    // 조회한 관을 통해 모든 관 예매 여부
    @Query("SELECT NEW pcm.open_movie.domain.dto.OpenCinemaRoomDateTimeDTO" +
            "(ocr.openCinemaRoomId, ocr.cinemaRoom.cinemaRoomName, ocr.openMovieDate) " +
            "FROM OpenMovie om " +
            "LEFT JOIN FETCH OpenCinemaRoom ocr ON om.openMovieId = ocr.openMovie.openMovieId AND ocr.cinemaRoom.cinema.cinemaId = :cinemaId " +
            "AND TO_CHAR(ocr.openMovieDate, 'yyyy-MM-dd') = :openMovieDate AND ocr.openMovieDate >= NOW() " +
            "LEFT JOIN FETCH CinemaRoom cr ON ocr.cinemaRoom.cinemaRoomId = cr.cinemaRoomId AND cr.cinema.cinemaId = :cinemaId " +
            "WHERE om.openMovieId =:openMovieId")
    List<OpenCinemaRoomDateTimeDTO> findOpenCinemaRoomList
    (@Param("openMovieId") Long openMovieId, @Param("openMovieDate") String openMovieDate, @Param("cinemaId") Long cinemaId);

    @Query("SELECT NEW pcm.open_movie.domain.dto.OpenCinemaRoomSiteStatusDTO" +
            "(ocr.openCinemaRoomId, crs.cinemaRoomSiteName, mr.movieReserveId) " +
            "FROM OpenCinemaRoom ocr " +
            "LEFT JOIN FETCH CinemaRoomSite crs ON ocr.cinemaRoom.cinemaRoomId = crs.cinemaRoom.cinemaRoomId " +
            "LEFT JOIN FETCH MovieReserve mr ON ocr.openCinemaRoomId = mr.openCinemaRoom.openCinemaRoomId " +
            "AND crs.cinemaRoomSiteId = mr.cinemaRoomSite.cinemaRoomSiteId " +
            "WHERE ocr.openCinemaRoomId IN :openCinemaRoomIdList")
    List<OpenCinemaRoomSiteStatusDTO> findOpenCinemaRoomSiteStatusList(@Param("openCinemaRoomIdList") List<Long> openCinemaRoomIdList);

    @Query("SELECT NEW pcm.open_movie.domain.dto.ReserveOpenCinemaRoomDTO" +
            "(crs.cinemaRoomSiteId, crs.cinemaRoomSiteName, mr.cinemaRoomSite.cinemaRoomSiteName) " +
            "FROM OpenCinemaRoom ocr " +
            "LEFT JOIN FETCH CinemaRoomSite crs ON ocr.cinemaRoom.cinemaRoomId = crs.cinemaRoom.cinemaRoomId " +
            "LEFT JOIN FETCH MovieReserve mr ON ocr.openCinemaRoomId = mr.openCinemaRoom.openCinemaRoomId " +
            "AND crs.cinemaRoom.cinemaRoomId = crs.cinemaRoom.cinemaRoomId AND crs.cinemaRoomSiteId = mr.cinemaRoomSite.cinemaRoomSiteId " +
            "WHERE ocr.openCinemaRoomId = :openCinemaRoomId")
    List<ReserveOpenCinemaRoomDTO> findReserveOpenCinemaRoomList(@Param("openCinemaRoomId") Long openCinemaRoomId);

    @Query("SELECT NEW pcm.open_movie.domain.dto.OpenCinemaRoomSiteDTO(ocr.openCinemaRoomId, crs.cinemaRoomSiteName, mr.movieReserveId) " +
            "FROM OpenCinemaRoom ocr LEFT JOIN FETCH MovieReserve mr " +
            "ON ocr.openCinemaRoomId = mr.openCinemaRoom.openCinemaRoomId " +
            "LEFT JOIN FETCH CinemaRoomSite crs " +
            "ON ocr.cinemaRoom.cinemaRoomId = crs.cinemaRoom.cinemaRoomId " +
            "WHERE ocr.openCinemaRoomId IN :openCinemaRoomIdList")
    List<OpenCinemaRoomSiteDTO> findOpenCinemaRoomSiteList(@Param("openCinemaRoomIdList") List<Long> openCinemaRoomIdList);

    @Query("SELECT NEW pcm.open_movie.domain.dto.OpenCinemaRoomAndOpenMovieDTO" +
            "(ocr.openCinemaRoomId, ocr.openMovieDate, c.cinemaAddress, c.cinemaName, cr.cinemaRoomName, om.openMovieTitle) " +
            "FROM OpenCinemaRoom ocr " +
            "LEFT JOIN FETCH CinemaRoom cr ON ocr.cinemaRoom.cinemaRoomId = cr.cinemaRoomId " +
            "LEFT JOIN FETCH Cinema c ON cr.cinema.cinemaId = c.cinemaId " +
            "LEFT JOIN FETCH OpenMovie om ON ocr.openMovie.openMovieId = om.openMovieId " +
            "WHERE ocr.openCinemaRoomId = :openCinemaRoomId")
    OpenCinemaRoomAndOpenMovieDTO findOpenCinemaRoomAndOpenMovie(@Param("openCinemaRoomId") Long openCinemaRoomId);

}
