package pcm.open_movie.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QOpenMovie is a Querydsl query type for OpenMovie
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOpenMovie extends EntityPathBase<OpenMovie> {

    private static final long serialVersionUID = 1762633766L;

    public static final QOpenMovie openMovie = new QOpenMovie("openMovie");

    public final NumberPath<Long> openMovieId = createNumber("openMovieId", Long.class);

    public final StringPath openMoviePostPath = createString("openMoviePostPath");

    public final StringPath openMovieTitle = createString("openMovieTitle");

    public QOpenMovie(String variable) {
        super(OpenMovie.class, forVariable(variable));
    }

    public QOpenMovie(Path<? extends OpenMovie> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOpenMovie(PathMetadata metadata) {
        super(OpenMovie.class, metadata);
    }

}

