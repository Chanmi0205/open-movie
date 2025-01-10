package pcm.open_movie.domain.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Setter
@Getter
public class OpenCinemaRoomAndSiteDTO {

    private Long openCinemaRoomId;
    private String cinemaRoomName;
    private LocalDateTime openCinemaRoomStartDateTime;
    private String openCinemaRoomSite;
    private String openCinemaRoomSiteStatus;

    public OpenCinemaRoomAndSiteDTO() {
    }

    public OpenCinemaRoomAndSiteDTO(Long openCinemaRoomId, String cinemaRoomName, LocalDateTime openCinemaRoomStartDateTime, String openCinemaRoomSite, String openCinemaRoomSiteStatus) {
        this.openCinemaRoomId = openCinemaRoomId;
        this.cinemaRoomName = cinemaRoomName;
        this.openCinemaRoomStartDateTime = openCinemaRoomStartDateTime;
        this.openCinemaRoomSite = openCinemaRoomSite;
        this.openCinemaRoomSiteStatus = openCinemaRoomSiteStatus;
    }
}
