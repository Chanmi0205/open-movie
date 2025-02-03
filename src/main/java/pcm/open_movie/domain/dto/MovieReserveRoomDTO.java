package pcm.open_movie.domain.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
public class MovieReserveRoomDTO {

    private Long openCinemaRoomId;

    private String openMovieTitle;
    private LocalDateTime openMovieDate;
    private String cinemaAddress;
    private String cinemaName;
    private String cinemaRoomName;

    private String openMovieDateText;

    public MovieReserveRoomDTO() {
    }

    @QueryProjection
    public MovieReserveRoomDTO(Long openCinemaRoomId, String openMovieTitle, LocalDateTime openMovieDate, String cinemaAddress, String cinemaName, String cinemaRoomName) {
        this.openCinemaRoomId = openCinemaRoomId;
        this.openMovieTitle = openMovieTitle;
        this.openMovieDate = openMovieDate;
        this.cinemaAddress = cinemaAddress;
        this.cinemaName = cinemaName;
        this.cinemaRoomName = cinemaRoomName;
    }
}
