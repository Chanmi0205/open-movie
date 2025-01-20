package pcm.open_movie.domain.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class MovieReserveSiteDTO {

    private Long openCinemaRoomId;
    private String cinemaRoomSiteName;

    public MovieReserveSiteDTO() {
    }

    public MovieReserveSiteDTO(Long openCinemaRoomId, String cinemaRoomSiteName) {
        this.openCinemaRoomId = openCinemaRoomId;
        this.cinemaRoomSiteName = cinemaRoomSiteName;
    }
}
