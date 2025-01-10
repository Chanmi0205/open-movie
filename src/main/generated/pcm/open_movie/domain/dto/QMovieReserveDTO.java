package pcm.open_movie.domain.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * pcm.open_movie.domain.dto.QMovieReserveDTO is a Querydsl Projection type for MovieReserveDTO
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QMovieReserveDTO extends ConstructorExpression<MovieReserveDTO> {

    private static final long serialVersionUID = 1128519481L;

    public QMovieReserveDTO(com.querydsl.core.types.Expression<String> cinemaName, com.querydsl.core.types.Expression<String> cinemaAddress, com.querydsl.core.types.Expression<String> cinemaRoomName, com.querydsl.core.types.Expression<String> cinemaRoomSiteName, com.querydsl.core.types.Expression<String> openMovieTitle, com.querydsl.core.types.Expression<java.time.LocalDateTime> openMovieDate) {
        super(MovieReserveDTO.class, new Class<?>[]{String.class, String.class, String.class, String.class, String.class, java.time.LocalDateTime.class}, cinemaName, cinemaAddress, cinemaRoomName, cinemaRoomSiteName, openMovieTitle, openMovieDate);
    }

}

