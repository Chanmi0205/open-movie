package pcm.open_movie;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.AuditorAware;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import pcm.TestDataInit;
import pcm.open_movie.domain.entity.Cinema;
import pcm.open_movie.repository.cinema.CinemaRepository;
import pcm.open_movie.repository.cinema.CinemaRoomRepository;
import pcm.open_movie.repository.cinema.CinemaRoomSiteRepository;
import pcm.open_movie.repository.member.MemberRepository;
import pcm.open_movie.repository.member.MovieReserveRepository;
import pcm.open_movie.repository.open.OpenCinemaRoomRepository;
import pcm.open_movie.repository.open.OpenMovieRepository;

import java.util.Optional;
import java.util.UUID;

@SpringBootTest
class OpenMovieApplicationTests {

	@Test
	void contextLoads() {
	}

}
