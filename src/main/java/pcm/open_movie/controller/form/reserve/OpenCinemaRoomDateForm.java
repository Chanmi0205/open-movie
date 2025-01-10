package pcm.open_movie.controller.form.reserve;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

// 사용자가 선택한 날짜(yyyy-MM-dd)
@Data
@Getter
@Setter
public class OpenCinemaRoomDateForm {

    @NotBlank
    @NotNull
    private LocalDateTime openDate;

    public OpenCinemaRoomDateForm() {
    }

    public OpenCinemaRoomDateForm(LocalDateTime openDate) {
        this.openDate = openDate;
    }
}
