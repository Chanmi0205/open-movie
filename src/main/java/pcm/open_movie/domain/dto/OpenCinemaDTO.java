package pcm.open_movie.domain.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class OpenCinemaDTO {

    private Long cinemaId;
    private String cinemaAddress;
    private String cinemaName;

    public OpenCinemaDTO() {
    }

    public OpenCinemaDTO(Long cinemaId, String cinemaAddress, String cinemaName) {
        this.cinemaId = cinemaId;
        this.cinemaAddress = cinemaAddress;
        this.cinemaName = cinemaName;
    }
}
