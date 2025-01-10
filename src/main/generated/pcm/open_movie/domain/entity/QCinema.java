package pcm.open_movie.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCinema is a Querydsl query type for Cinema
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCinema extends EntityPathBase<Cinema> {

    private static final long serialVersionUID = 348087761L;

    public static final QCinema cinema = new QCinema("cinema");

    public final StringPath cinemaAddress = createString("cinemaAddress");

    public final NumberPath<Long> cinemaId = createNumber("cinemaId", Long.class);

    public final StringPath cinemaName = createString("cinemaName");

    public final ListPath<CinemaRoom, QCinemaRoom> cinemaRoomList = this.<CinemaRoom, QCinemaRoom>createList("cinemaRoomList", CinemaRoom.class, QCinemaRoom.class, PathInits.DIRECT2);

    public QCinema(String variable) {
        super(Cinema.class, forVariable(variable));
    }

    public QCinema(Path<? extends Cinema> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCinema(PathMetadata metadata) {
        super(Cinema.class, metadata);
    }

}

