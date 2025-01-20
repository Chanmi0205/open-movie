package pcm.open_movie.domain.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class OpenCinemaRoomDateTimeDTO {

    private Long openCinemaRoomId;
    private String cinemaRoomName;
    private LocalDateTime openCinemaRoomStartDateTime;
    private String openCinemaRoomStartDateTimeText;

    public OpenCinemaRoomDateTimeDTO() {
    }

    public OpenCinemaRoomDateTimeDTO(Long openCinemaRoomId, String cinemaRoomName, LocalDateTime openCinemaRoomStartDateTime) {
        this.openCinemaRoomId = openCinemaRoomId;
        this.cinemaRoomName = cinemaRoomName;
        this.openCinemaRoomStartDateTime = openCinemaRoomStartDateTime;
    }
}
