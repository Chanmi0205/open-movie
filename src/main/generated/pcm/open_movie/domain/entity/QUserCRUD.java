package pcm.open_movie.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserCRUD is a Querydsl query type for UserCRUD
 */
@Generated("com.querydsl.codegen.DefaultSupertypeSerializer")
public class QUserCRUD extends EntityPathBase<UserCRUD> {

    private static final long serialVersionUID = 848886889L;

    public static final QUserCRUD userCRUD = new QUserCRUD("userCRUD");

    public final StringPath createdBy = createString("createdBy");

    public final DateTimePath<java.util.Date> createdDate = createDateTime("createdDate", java.util.Date.class);

    public final StringPath lastModifiedBy = createString("lastModifiedBy");

    public final DateTimePath<java.util.Date> lastModifiedDate = createDateTime("lastModifiedDate", java.util.Date.class);

    public QUserCRUD(String variable) {
        super(UserCRUD.class, forVariable(variable));
    }

    public QUserCRUD(Path<? extends UserCRUD> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserCRUD(PathMetadata metadata) {
        super(UserCRUD.class, metadata);
    }

}

