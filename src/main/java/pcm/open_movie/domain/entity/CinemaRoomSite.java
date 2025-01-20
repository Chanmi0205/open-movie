package pcm.open_movie.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class CinemaRoomSite {

    @GeneratedValue
    @Id
    private Long cinemaRoomSiteId;

    private String cinemaRoomSiteName;

    @ManyToOne
    @JoinColumn(name = "CINEMA_ROOM_ID")
    private CinemaRoom cinemaRoom;

    public CinemaRoomSite() {
    }

    public CinemaRoomSite(String cinemaRoomSiteName, CinemaRoom cinemaRoom) {
        this.cinemaRoomSiteName = cinemaRoomSiteName;
        this.cinemaRoom = cinemaRoom;
    }
}
