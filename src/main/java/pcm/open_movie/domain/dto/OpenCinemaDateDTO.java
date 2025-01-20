package pcm.open_movie.domain.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Setter
@Getter
public class OpenCinemaDateDTO {

    private Long cinemaId;
    private String cinemaAddress;
    private String cinemaName;
    private String openMovieDateFormat;

    public OpenCinemaDateDTO() {
    }

    public OpenCinemaDateDTO(Long cinemaId, String cinemaAddress, String cinemaName, String openMovieDateFormat) {
        this.cinemaId = cinemaId;
        this.cinemaAddress = cinemaAddress;
        this.cinemaName = cinemaName;
        this.openMovieDateFormat = openMovieDateFormat;
    }
}
