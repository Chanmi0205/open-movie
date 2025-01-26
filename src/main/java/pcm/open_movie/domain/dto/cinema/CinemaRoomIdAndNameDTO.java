package pcm.open_movie.domain.dto.cinema;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class CinemaRoomIdAndNameDTO {

    @NotNull
    private Long cinemaRoomId;
    @NotNull
    private String cinemaRoomName;

    public CinemaRoomIdAndNameDTO() {
    }

    public CinemaRoomIdAndNameDTO(Long cinemaRoomId, String cinemaRoomName) {
        this.cinemaRoomId = cinemaRoomId;
        this.cinemaRoomName = cinemaRoomName;
    }
}
