package pcm.open_movie.domain.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
public class MovieReserveDTO {

    private String cinemaName;
    private String cinemaAddress;
    private String cinemaRoomName;
    private String cinemaRoomSiteName;
    private String openMovieTitle;
    private LocalDateTime openMovieDate;

    public MovieReserveDTO() {
    }

    @QueryProjection
    public MovieReserveDTO(String cinemaName, String cinemaAddress, String cinemaRoomName, String cinemaRoomSiteName, String openMovieTitle, LocalDateTime openMovieDate) {
        this.cinemaName = cinemaName;
        this.cinemaAddress = cinemaAddress;
        this.cinemaRoomName = cinemaRoomName;
        this.cinemaRoomSiteName = cinemaRoomSiteName;
        this.openMovieTitle = openMovieTitle;
        this.openMovieDate = openMovieDate;
    }
}
