package pcm.open_movie.domain.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class OpenCinemaRoomSiteSelectDTO {

    private String cinemaRoomSiteName;
    private Long siteReserveTF;

    public OpenCinemaRoomSiteSelectDTO() {
    }

    public OpenCinemaRoomSiteSelectDTO(String cinemaRoomSiteName, Long siteReserveTF) {
        this.cinemaRoomSiteName = cinemaRoomSiteName;
        this.siteReserveTF = siteReserveTF;
    }
}
