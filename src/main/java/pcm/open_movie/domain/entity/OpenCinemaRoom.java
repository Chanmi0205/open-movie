package pcm.open_movie.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
public class OpenCinemaRoom {

    @GeneratedValue
    @Id
    private Long openCinemaRoomId;

    private LocalDateTime openMovieDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CINEMA_ROOM_ID")
    private CinemaRoom cinemaRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OPEN_MOVIE_ID")
    private OpenMovie openMovie;

    public OpenCinemaRoom() {
    }

    public OpenCinemaRoom(LocalDateTime openMovieDate, CinemaRoom cinemaRoom, OpenMovie openMovie) {
        this.openMovieDate = openMovieDate;
        this.cinemaRoom = cinemaRoom;
        this.openMovie = openMovie;
    }
}
