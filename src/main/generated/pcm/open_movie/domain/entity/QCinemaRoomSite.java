package pcm.open_movie.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCinemaRoomSite is a Querydsl query type for CinemaRoomSite
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCinemaRoomSite extends EntityPathBase<CinemaRoomSite> {

    private static final long serialVersionUID = -353478733L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCinemaRoomSite cinemaRoomSite = new QCinemaRoomSite("cinemaRoomSite");

    public final QCinemaRoom cinemaRoom;

    public final NumberPath<Long> CinemaRoomSiteId = createNumber("CinemaRoomSiteId", Long.class);

    public final StringPath cinemaRoomSiteName = createString("cinemaRoomSiteName");

    public QCinemaRoomSite(String variable) {
        this(CinemaRoomSite.class, forVariable(variable), INITS);
    }

    public QCinemaRoomSite(Path<? extends CinemaRoomSite> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCinemaRoomSite(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCinemaRoomSite(PathMetadata metadata, PathInits inits) {
        this(CinemaRoomSite.class, metadata, inits);
    }

    public QCinemaRoomSite(Class<? extends CinemaRoomSite> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.cinemaRoom = inits.isInitialized("cinemaRoom") ? new QCinemaRoom(forProperty("cinemaRoom"), inits.get("cinemaRoom")) : null;
    }

}

