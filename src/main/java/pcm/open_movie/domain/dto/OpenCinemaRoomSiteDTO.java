package pcm.open_movie.domain.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class OpenCinemaRoomSiteDTO {

    private Long openCinemaRoomId;
    private String cinemaRoomSiteName;
    private Long siteReserveTF;

    public OpenCinemaRoomSiteDTO() {
    }

    public OpenCinemaRoomSiteDTO(Long openCinemaRoomId, String cinemaRoomSiteName, Long siteReserveTF) {
        this.openCinemaRoomId = openCinemaRoomId;
        this.cinemaRoomSiteName = cinemaRoomSiteName;
        this.siteReserveTF = siteReserveTF;
    }
}
