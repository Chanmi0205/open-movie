package pcm.open_movie.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class OpenMovie {

    @GeneratedValue
    @Id
    private Long openMovieId;

    private String openMovieTitle;

    private String openMoviePostPath;

    public OpenMovie() {
    }

    public OpenMovie(String openMovieTitle, String openMoviePostPath) {
        this.openMovieTitle = openMovieTitle;
        this.openMoviePostPath = openMoviePostPath;
    }
}
