package pcm.open_movie.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Cinema {

    @GeneratedValue
    @Id
    @Getter
    private Long cinemaId;

    private String cinemaName;

    private String cinemaAddress;

    @OneToMany(mappedBy = "cinema")
    private List<CinemaRoom> cinemaRoomList = new ArrayList<>();

    public Cinema() {
    }

    public Cinema(String cinemaName, String cinemaAddress) {
        this.cinemaName = cinemaName;
        this.cinemaAddress = cinemaAddress;
    }
}
