package pcm.open_movie.domain.dto.cinema;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class CinemaRoomIdAndSiteNameDTO {

    private Long cinemaRoomId;
    private String cinemaRoomSiteName;

    public CinemaRoomIdAndSiteNameDTO() {
    }

    public CinemaRoomIdAndSiteNameDTO(Long cinemaRoomId, String cinemaRoomSiteName) {
        this.cinemaRoomId = cinemaRoomId;
        this.cinemaRoomSiteName = cinemaRoomSiteName;
    }
}
