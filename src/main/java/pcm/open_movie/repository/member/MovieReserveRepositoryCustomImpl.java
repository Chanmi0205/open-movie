package pcm.open_movie.repository.member;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import pcm.open_movie.domain.dto.MovieReserveDTO;
import pcm.open_movie.domain.dto.MovieReserveRoomDTO;
import pcm.open_movie.domain.dto.QMovieReserveDTO;
import pcm.open_movie.domain.dto.QMovieReserveRoomDTO;
import pcm.open_movie.domain.entity.*;

import java.time.LocalDateTime;
import java.util.List;

import static pcm.open_movie.domain.entity.QCinema.*;
import static pcm.open_movie.domain.entity.QCinemaRoom.*;
import static pcm.open_movie.domain.entity.QCinemaRoomSite.*;
import static pcm.open_movie.domain.entity.QMember.*;
import static pcm.open_movie.domain.entity.QMovieReserve.*;
import static pcm.open_movie.domain.entity.QOpenCinemaRoom.*;
import static pcm.open_movie.domain.entity.QOpenMovie.*;

public class MovieReserveRepositoryCustomImpl implements MovieReserveRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public MovieReserveRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<MovieReserveRoomDTO> findMovieReserveByMemberId(String memberId, boolean openMovieTF, Pageable pageable) {

        List<MovieReserveRoomDTO> content = queryFactory
                .select(new QMovieReserveRoomDTO(openCinemaRoom.openCinemaRoomId, openMovie.openMovieTitle, openCinemaRoom.openMovieDate,
                        cinema.cinemaAddress, cinema.cinemaName, cinemaRoom.cinemaRoomName))
                .distinct()
                .from(member)
                .leftJoin(member.movieReserveList, movieReserve)
                .leftJoin(movieReserve.openCinemaRoom, openCinemaRoom)
                .leftJoin(openCinemaRoom.openMovie, openMovie)
                .leftJoin(openCinemaRoom.cinemaRoom, cinemaRoom)
                .leftJoin(cinemaRoom.cinema, cinema)
                .where(member.memberId.eq(memberId), dateStatus(openMovieTF))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(openCinemaRoom.openMovieDate.desc())
                .fetch();

            // 수정해야 할 부분
            long count = queryFactory.select(movieReserve.openCinemaRoom)
                .from(movieReserve)
                .leftJoin(movieReserve.openCinemaRoom, openCinemaRoom)
                .where(movieReserve.member.memberId.eq(memberId), dateStatus(openMovieTF))
                .groupBy(movieReserve.openCinemaRoom)
                .fetchCount();

        return new PageImpl<>(content, pageable, count);
    }

    private BooleanExpression dateStatus(boolean openMovieTF) {
        LocalDateTime now = LocalDateTime.now();
        return openMovieTF ? openCinemaRoom.openMovieDate.after(now) : openCinemaRoom.openMovieDate.before(now);
    }
}
