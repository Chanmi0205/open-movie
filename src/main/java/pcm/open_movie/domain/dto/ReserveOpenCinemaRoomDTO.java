package pcm.open_movie.domain.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class ReserveOpenCinemaRoomDTO {

    private Long cinemaRoomSiteId;
    private String cinemaRoomSiteName;
    private String cinemaRoomSiteStatus;

    public ReserveOpenCinemaRoomDTO() {
    }

    public ReserveOpenCinemaRoomDTO(Long cinemaRoomSiteId, String cinemaRoomSiteName, String cinemaRoomSiteStatus) {
        this.cinemaRoomSiteId = cinemaRoomSiteId;
        this.cinemaRoomSiteName = cinemaRoomSiteName;
        this.cinemaRoomSiteStatus = cinemaRoomSiteStatus;
    }
}
