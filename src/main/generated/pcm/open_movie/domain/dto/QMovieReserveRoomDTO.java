package pcm.open_movie.domain.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * pcm.open_movie.domain.dto.QMovieReserveRoomDTO is a Querydsl Projection type for MovieReserveRoomDTO
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QMovieReserveRoomDTO extends ConstructorExpression<MovieReserveRoomDTO> {

    private static final long serialVersionUID = -683462818L;

    public QMovieReserveRoomDTO(com.querydsl.core.types.Expression<Long> openCinemaRoomId, com.querydsl.core.types.Expression<String> openMovieTitle, com.querydsl.core.types.Expression<java.time.LocalDateTime> openMovieDate, com.querydsl.core.types.Expression<String> cinemaAddress, com.querydsl.core.types.Expression<String> cinemaName, com.querydsl.core.types.Expression<String> cinemaRoomName) {
        super(MovieReserveRoomDTO.class, new Class<?>[]{long.class, String.class, java.time.LocalDateTime.class, String.class, String.class, String.class}, openCinemaRoomId, openMovieTitle, openMovieDate, cinemaAddress, cinemaName, cinemaRoomName);
    }

}

