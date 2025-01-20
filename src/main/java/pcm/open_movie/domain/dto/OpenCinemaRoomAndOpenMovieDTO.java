package pcm.open_movie.domain.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Setter
@Getter
public class OpenCinemaRoomAndOpenMovieDTO {

    private Long openCinemaRoomId;
    private LocalDateTime openMovieDate;
    private String cinemaAddress;
    private String cinemaName;
    private String cinemaRoomName;
    private String openMovieTitle;

    public OpenCinemaRoomAndOpenMovieDTO() {
    }

    public OpenCinemaRoomAndOpenMovieDTO(Long openCinemaRoomId, LocalDateTime openMovieDate, String cinemaAddress, String cinemaName, String cinemaRoomName, String openMovieTitle) {
        this.openCinemaRoomId = openCinemaRoomId;
        this.openMovieDate = openMovieDate;
        this.cinemaAddress = cinemaAddress;
        this.cinemaName = cinemaName;
        this.cinemaRoomName = cinemaRoomName;
        this.openMovieTitle = openMovieTitle;
    }
}
