package pcm.open_movie.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCinemaRoom is a Querydsl query type for CinemaRoom
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCinemaRoom extends EntityPathBase<CinemaRoom> {

    private static final long serialVersionUID = 942475852L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCinemaRoom cinemaRoom = new QCinemaRoom("cinemaRoom");

    public final QCinema cinema;

    public final NumberPath<Long> cinemaRoomId = createNumber("cinemaRoomId", Long.class);

    public final StringPath cinemaRoomName = createString("cinemaRoomName");

    public QCinemaRoom(String variable) {
        this(CinemaRoom.class, forVariable(variable), INITS);
    }

    public QCinemaRoom(Path<? extends CinemaRoom> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCinemaRoom(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCinemaRoom(PathMetadata metadata, PathInits inits) {
        this(CinemaRoom.class, metadata, inits);
    }

    public QCinemaRoom(Class<? extends CinemaRoom> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.cinema = inits.isInitialized("cinema") ? new QCinema(forProperty("cinema")) : null;
    }

}

