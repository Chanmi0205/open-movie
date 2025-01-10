package pcm.open_movie.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class CinemaRoom {

    @GeneratedValue
    @Id
    private Long cinemaRoomId;

    private String cinemaRoomName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CINEMA_ID")
    private Cinema cinema;

    public CinemaRoom() {}

    public CinemaRoom(String cinemaRoomName, Cinema cinema) {
        this.cinemaRoomName = cinemaRoomName;
        this.cinema = cinema;
    }

    //    // 양방향 연관관계
//    public void changeCinema(Cinema cinema) {
//        this.cinema = cinema;
//        cinema.getCinemaRoomList().add(this);
//    }
}
