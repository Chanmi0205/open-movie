package pcm.open_movie.domain.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class OpenCinemaRoomSiteStatusDTO {

    private Long openCinemaRoomId;
    private String openCinemaRoomSite;
    private String openCinemaRoomStatus;

    public OpenCinemaRoomSiteStatusDTO() {
    }

    public OpenCinemaRoomSiteStatusDTO(Long openCinemaRoomId, String openCinemaRoomSite, String openCinemaRoomStatus) {
        this.openCinemaRoomId = openCinemaRoomId;
        this.openCinemaRoomSite = openCinemaRoomSite;
        this.openCinemaRoomStatus = openCinemaRoomStatus;
    }
}
