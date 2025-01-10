package pcm.open_movie.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOpenCinemaRoom is a Querydsl query type for OpenCinemaRoom
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOpenCinemaRoom extends EntityPathBase<OpenCinemaRoom> {

    private static final long serialVersionUID = -749025226L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOpenCinemaRoom openCinemaRoom = new QOpenCinemaRoom("openCinemaRoom");

    public final QCinemaRoom cinemaRoom;

    public final NumberPath<Long> openCinemaRoomId = createNumber("openCinemaRoomId", Long.class);

    public final QOpenMovie openMovie;

    public final DateTimePath<java.time.LocalDateTime> openMovieDate = createDateTime("openMovieDate", java.time.LocalDateTime.class);

    public QOpenCinemaRoom(String variable) {
        this(OpenCinemaRoom.class, forVariable(variable), INITS);
    }

    public QOpenCinemaRoom(Path<? extends OpenCinemaRoom> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOpenCinemaRoom(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOpenCinemaRoom(PathMetadata metadata, PathInits inits) {
        this(OpenCinemaRoom.class, metadata, inits);
    }

    public QOpenCinemaRoom(Class<? extends OpenCinemaRoom> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.cinemaRoom = inits.isInitialized("cinemaRoom") ? new QCinemaRoom(forProperty("cinemaRoom"), inits.get("cinemaRoom")) : null;
        this.openMovie = inits.isInitialized("openMovie") ? new QOpenMovie(forProperty("openMovie")) : null;
    }

}

