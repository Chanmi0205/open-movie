package pcm.open_movie.controller.form.reserve;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class OpenCinemaIdAndDateForm {

    private Long openCinemaRoomId;
    private String reserveDate;

    public OpenCinemaIdAndDateForm() {
    }

    public OpenCinemaIdAndDateForm(Long openCinemaRoomId, String reserveDate) {
        this.openCinemaRoomId = openCinemaRoomId;
        this.reserveDate = reserveDate;
    }
}
