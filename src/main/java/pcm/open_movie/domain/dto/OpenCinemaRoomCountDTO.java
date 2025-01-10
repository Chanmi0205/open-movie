package pcm.open_movie.domain.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Setter
@Getter
public class OpenCinemaRoomCountDTO {

    private Long openCinemaId;
    private Long openCinemaCount;

    public OpenCinemaRoomCountDTO() {
    }

    public OpenCinemaRoomCountDTO(Long openCinemaId, Long openCinemaCount) {
        this.openCinemaId = openCinemaId;
        this.openCinemaCount = openCinemaCount;
    }
}
