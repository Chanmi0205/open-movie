package pcm.open_movie.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMovieReserve is a Querydsl query type for MovieReserve
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMovieReserve extends EntityPathBase<MovieReserve> {

    private static final long serialVersionUID = 1725348428L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMovieReserve movieReserve = new QMovieReserve("movieReserve");

    public final QUserCRUD _super = new QUserCRUD(this);

    public final QCinemaRoomSite cinemaRoomSite;

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.util.Date> createdDate = _super.createdDate;

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final QMember member;

    public final NumberPath<Long> movieReserveId = createNumber("movieReserveId", Long.class);

    public final QOpenCinemaRoom openCinemaRoom;

    public QMovieReserve(String variable) {
        this(MovieReserve.class, forVariable(variable), INITS);
    }

    public QMovieReserve(Path<? extends MovieReserve> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMovieReserve(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMovieReserve(PathMetadata metadata, PathInits inits) {
        this(MovieReserve.class, metadata, inits);
    }

    public QMovieReserve(Class<? extends MovieReserve> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.cinemaRoomSite = inits.isInitialized("cinemaRoomSite") ? new QCinemaRoomSite(forProperty("cinemaRoomSite"), inits.get("cinemaRoomSite")) : null;
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
        this.openCinemaRoom = inits.isInitialized("openCinemaRoom") ? new QOpenCinemaRoom(forProperty("openCinemaRoom"), inits.get("openCinemaRoom")) : null;
    }

}

