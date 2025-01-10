package pcm.open_movie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.AuditorAware;
import org.springframework.scheduling.annotation.EnableScheduling;
import pcm.TestDataInit;
import pcm.open_movie.repository.cinema.CinemaRepository;
import pcm.open_movie.repository.cinema.CinemaRoomRepository;
import pcm.open_movie.repository.cinema.CinemaRoomSiteRepository;
import pcm.open_movie.repository.member.MemberRepository;
import pcm.open_movie.repository.member.MovieReserveRepository;
import pcm.open_movie.repository.open.OpenCinemaRoomRepository;
import pcm.open_movie.repository.open.OpenMovieRepository;

import java.util.Optional;
import java.util.UUID;

@SpringBootApplication
@EnableScheduling
public class OpenMovieApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpenMovieApplication.class, args);
	}

	@Bean
	public AuditorAware<String> auditorProvider() {
		return () -> Optional.of(UUID.randomUUID().toString());
	}


	@Bean
	@Profile("local")
	public TestDataInit testDataInit(
			CinemaRepository cinemaRepository, CinemaRoomRepository cinemaRoomRepository, CinemaRoomSiteRepository cinemaRoomSiteRepository,
			MemberRepository memberRepository, MovieReserveRepository movieReserveRepository, OpenCinemaRoomRepository openCinemaRoomRepository,
			OpenMovieRepository openMovieRepository) {
		return new TestDataInit(cinemaRepository, cinemaRoomRepository, cinemaRoomSiteRepository, memberRepository,
													movieReserveRepository, openCinemaRoomRepository, openMovieRepository);
	}

}
