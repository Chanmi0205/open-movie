package pcm.open_movie.domain.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
public class OpenCinemaRoomDTO {

    private LocalDateTime openCinemaDate;
    private String openCinemaDateFormat;
    private String cinemaRoomName;
    private Long openCinemaRoomId;

    public OpenCinemaRoomDTO() {}

    public OpenCinemaRoomDTO(LocalDateTime openCinemaDate, String cinemaRoomName, Long openCinemaRoomId) {
        this.openCinemaDate = openCinemaDate;
        this.cinemaRoomName = cinemaRoomName;
        this.openCinemaRoomId = openCinemaRoomId;
    }
}
