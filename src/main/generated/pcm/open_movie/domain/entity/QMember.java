package pcm.open_movie.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = 630652282L;

    public static final QMember member = new QMember("member1");

    public final QUserCRUD _super = new QUserCRUD(this);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.util.Date> createdDate = _super.createdDate;

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath memberId = createString("memberId");

    public final StringPath memberName = createString("memberName");

    public final ListPath<MovieReserve, QMovieReserve> movieReserveList = this.<MovieReserve, QMovieReserve>createList("movieReserveList", MovieReserve.class, QMovieReserve.class, PathInits.DIRECT2);

    public final StringPath password = createString("password");

    public final StringPath phoneNum = createString("phoneNum");

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

