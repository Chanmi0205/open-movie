package pcm.open_movie.controller.form.reserve;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
public class ReserveForm {

    @NotNull
    @NotBlank
    private List<Long> cinemaRoomSiteIdList = new ArrayList<>();

    @NotNull
    @NotBlank
    private Long openCinemaRoomId;

    public ReserveForm() {
    }

    public ReserveForm(List<Long> cinemaRoomSiteIdList, Long openCinemaRoomId) {
        this.cinemaRoomSiteIdList = cinemaRoomSiteIdList;
        this.openCinemaRoomId = openCinemaRoomId;
    }
}
