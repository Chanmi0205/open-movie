package pcm;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import pcm.open_movie.domain.entity.*;
import pcm.open_movie.repository.cinema.CinemaRepository;
import pcm.open_movie.repository.cinema.CinemaRoomRepository;
import pcm.open_movie.repository.cinema.CinemaRoomSiteRepository;
import pcm.open_movie.repository.member.MemberRepository;
import pcm.open_movie.repository.member.MovieReserveRepository;
import pcm.open_movie.repository.open.OpenCinemaRoomRepository;
import pcm.open_movie.repository.open.OpenMovieRepository;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
public class TestDataInit {

    private final CinemaRepository cinemaRepository;
    private final CinemaRoomRepository cinemaRoomRepository;
    private final CinemaRoomSiteRepository cinemaRoomSiteRepository;

    private final MemberRepository memberRepository;
    private final MovieReserveRepository movieReserveRepository; // 생략
    private final OpenCinemaRoomRepository openCinemaRoomRepository;

    private final OpenMovieRepository openMovieRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void initData() {

        Cinema cinema1 = new Cinema("PCM", "이웃마을 초코시티 큰건물");
        Cinema cinema2 = new Cinema("대충영화관", "저쪽마을 이쪽시티 작은건물");
        Cinema cinema3 = new Cinema("음뭘로할까고민중", "그냥마을 코딩시티 자바건물");

        cinemaRepository.save(cinema1);
        cinemaRepository.save(cinema2);
        cinemaRepository.save(cinema3);

        CinemaRoom cinema1Room1 = new CinemaRoom("1관", cinema1);
        CinemaRoom cinema1Room2 = new CinemaRoom("2관", cinema1);
        CinemaRoom cinema1Room3 = new CinemaRoom("3관", cinema1);

        CinemaRoom cinema2Room1 = new CinemaRoom("1관", cinema2);
        CinemaRoom cinema2Room2 = new CinemaRoom("2관", cinema2);

        CinemaRoom cinema3Room1 = new CinemaRoom("1관", cinema3);

        cinemaRoomRepository.save(cinema1Room1);
        cinemaRoomRepository.save(cinema1Room2);
        cinemaRoomRepository.save(cinema1Room3);
        cinemaRoomRepository.save(cinema2Room1);
        cinemaRoomRepository.save(cinema2Room2);
        cinemaRoomRepository.save(cinema3Room1);

        // cinemaRoomSite

        // 1관
        for(int rowNum=1; rowNum<=18; rowNum++) {
            for(int i=1; i<=10; i++) {
                if(rowNum==1&&i==9 || rowNum==1&&i==10 || rowNum==2&& i==10 || rowNum==18&i==9 || rowNum==17&&i==10 || rowNum==18&i==10) continue;
                int columnNum = 64;
                char columnName = (char) (columnNum + i);
                CinemaRoomSite cinema1Room1Site = new CinemaRoomSite(columnName + "-" + rowNum, cinema1Room1);
                CinemaRoomSite cinema2Room1Site = new CinemaRoomSite(columnName + "-" + rowNum, cinema2Room1);
                CinemaRoomSite cinema3Room1Site = new CinemaRoomSite(columnName + "-" + rowNum, cinema3Room1);
                cinemaRoomSiteRepository.save(cinema1Room1Site);
                cinemaRoomSiteRepository.save(cinema2Room1Site);
                cinemaRoomSiteRepository.save(cinema3Room1Site);
            }
        }

        // 2관
        for(int rowNum=1; rowNum<=18; rowNum++) {
            for(int i=1; i<=10; i++) {
                if(rowNum==1&&i==10 || rowNum==18&&i==10) continue;
                int columnNum = 64;
                char columnName = (char) (columnNum + i);
                CinemaRoomSite cinema1Room2Site = new CinemaRoomSite(columnName + "-" + rowNum, cinema1Room2);
                CinemaRoomSite cinema2Room2Site = new CinemaRoomSite(columnName + "-" + rowNum, cinema2Room2);
                cinemaRoomSiteRepository.save(cinema1Room2Site);
                cinemaRoomSiteRepository.save(cinema2Room2Site);
            }
        }

        // 3관
        for (int rowNum=1; rowNum<=18; rowNum++) {
            for (int i=1; i<=10; i++) {
                if(rowNum >= i) {
                    int columnNum = 64;
                    char columnName = (char) (columnNum + i);
                    CinemaRoomSite cinema1Room3Site = new CinemaRoomSite(columnName + "-" + rowNum, cinema1Room3);
                    cinemaRoomSiteRepository.save(cinema1Room3Site);
                }
            }
        }

        OpenMovie INCEPTION = new OpenMovie("인셉션", "INCEPTION");
        OpenMovie TITANIC = new OpenMovie("타이타닉", "TITANIC");
        OpenMovie TopGun_Maverick = new OpenMovie("탑건 : 매버릭", "TopGun_Maverick");
        OpenMovie Whiplash = new OpenMovie("위플래쉬", "Whiplash");

        openMovieRepository.save(INCEPTION);
        openMovieRepository.save(TITANIC);
        openMovieRepository.save(TopGun_Maverick);
        openMovieRepository.save(Whiplash);

        // openCinemaRoom

        LocalDateTime standardTime = LocalDateTime.of
                (LocalDateTime.now().getYear(), LocalDateTime.now().getMonth(), LocalDateTime.now().getDayOfMonth(), 6, 45);
        LocalDateTime standardTimePlusDay1 = LocalDateTime.of
                (LocalDateTime.now().getYear(), LocalDateTime.now().getMonth(), LocalDateTime.now().plusDays(1).getDayOfMonth(), 6, 45);

        // PCM 1관 | 타이타닉 5번 상영 | 오늘, 내일
        for(int openTimeChangeNum=1; openTimeChangeNum<=5; openTimeChangeNum++) {

            standardTime = standardTime.plusMinutes(25);
            standardTimePlusDay1 = standardTimePlusDay1.plusMinutes(25);
            OpenCinemaRoom openCinemaRoomToday = new OpenCinemaRoom(standardTime, cinema1Room1, TITANIC);
            OpenCinemaRoom openCinemaRoomP1 = new OpenCinemaRoom(standardTimePlusDay1, cinema1Room1, TITANIC);
            openCinemaRoomRepository.save(openCinemaRoomToday);
            openCinemaRoomRepository.save(openCinemaRoomP1);

            standardTime = standardTime.plusHours(3).plusMinutes(15);
            standardTimePlusDay1 = standardTimePlusDay1.plusHours(3).plusMinutes(15);

        }

        standardTime = LocalDateTime.of
                (LocalDateTime.now().getYear(), LocalDateTime.now().getMonth(), LocalDateTime.now().getDayOfMonth(), 6, 45);
        standardTimePlusDay1 = LocalDateTime.of
                (LocalDateTime.now().getYear(), LocalDateTime.now().getMonth(), LocalDateTime.now().plusDays(1).getDayOfMonth(), 6, 45);

        // 대충영화관 1관 | 인셉션 6번 상영 | 오늘, 내일
        for(int openTimeChangeNum=1; openTimeChangeNum<=6; openTimeChangeNum++) {

            standardTime = standardTime.plusMinutes(25);
            standardTimePlusDay1 = standardTimePlusDay1.plusMinutes(25);

            OpenCinemaRoom openCinemaRoomToday = new OpenCinemaRoom(standardTime, cinema1Room2, INCEPTION);
            OpenCinemaRoom openCinemaRoomP1 = new OpenCinemaRoom(standardTimePlusDay1, cinema1Room2, INCEPTION);
            openCinemaRoomRepository.save(openCinemaRoomToday);
            openCinemaRoomRepository.save(openCinemaRoomP1);

            standardTime = standardTime.plusHours(2).plusMinutes(28);
            standardTimePlusDay1 = standardTimePlusDay1.plusHours(2).plusMinutes(28);
        }

        standardTime = LocalDateTime.of
                (LocalDateTime.now().getYear(), LocalDateTime.now().getMonth(), LocalDateTime.now().getDayOfMonth(), 6, 45);
        standardTimePlusDay1 = LocalDateTime.of
                (LocalDateTime.now().getYear(), LocalDateTime.now().getMonth(), LocalDateTime.now().plusDays(1).getDayOfMonth(), 6, 45);

        // 음뭘로할까고민중 1관 | 탑건 : 매버릭 3번 사영, 위플래쉬 4번 상영 | 오늘, 내일
        for(int openTimeChangeNum=1; openTimeChangeNum<=3; openTimeChangeNum++) {

            standardTime = standardTime.plusMinutes(25);
            standardTimePlusDay1 = standardTimePlusDay1.plusMinutes(25);

            OpenCinemaRoom openCinemaRoomToday = new OpenCinemaRoom(standardTime, cinema1Room3, INCEPTION);
            OpenCinemaRoom openCinemaRoomP1 = new OpenCinemaRoom(standardTimePlusDay1, cinema1Room3, INCEPTION);
            openCinemaRoomRepository.save(openCinemaRoomToday);
            openCinemaRoomRepository.save(openCinemaRoomP1);

            standardTime = standardTime.plusHours(2).plusMinutes(10);
            standardTimePlusDay1 = standardTimePlusDay1.plusHours(2).plusMinutes(10);

        }

        for(int openTimeChangeNum=1; openTimeChangeNum<=3; openTimeChangeNum++) {

            standardTime = standardTime.plusMinutes(25);
            standardTimePlusDay1 = standardTimePlusDay1.plusMinutes(25);

            OpenCinemaRoom openCinemaRoomToday = new OpenCinemaRoom(standardTime, cinema2Room1, INCEPTION);
            OpenCinemaRoom openCinemaRoomP1 = new OpenCinemaRoom(standardTimePlusDay1, cinema2Room1, INCEPTION);
            openCinemaRoomRepository.save(openCinemaRoomToday);
            openCinemaRoomRepository.save(openCinemaRoomP1);

            standardTime = standardTime.plusHours(2).plusMinutes(10);
            standardTimePlusDay1 = standardTimePlusDay1.plusHours(2).plusMinutes(10);

        }

        LocalDateTime standardTime2 =
                LocalDateTime.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonth(), LocalDateTime.now().getDayOfMonth(), 14, 30);
        LocalDateTime standardTime2PlusDay1 =
                LocalDateTime.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonth(), LocalDateTime.now().plusDays(1).getDayOfMonth(), 14, 30);

        for(int openTimeChangeNum=1; openTimeChangeNum<=4; openTimeChangeNum++) {

            standardTime2 = standardTime2.plusMinutes(25);
            standardTime2PlusDay1 = standardTime2PlusDay1.plusMinutes(25);

            OpenCinemaRoom openCinemaRoomToday = new OpenCinemaRoom(standardTime2, cinema1Room3, Whiplash);
            OpenCinemaRoom openCinemaRoomP1 = new OpenCinemaRoom(standardTime2PlusDay1, cinema1Room3, Whiplash);
            openCinemaRoomRepository.save(openCinemaRoomToday);
            openCinemaRoomRepository.save(openCinemaRoomP1);

            standardTime2 = standardTime2.plusHours(1).plusMinutes(46);
            standardTime2PlusDay1 = standardTime2PlusDay1.plusHours(1).plusMinutes(46);

        }

        LocalDateTime standardTimePlusDay2 = LocalDateTime.of
                (LocalDateTime.now().getYear(), LocalDateTime.now().getMonth(), LocalDateTime.now().plusDays(2).getDayOfMonth(), 6, 45);

        LocalDateTime standardTimePlusDay3 = LocalDateTime.of
                (LocalDateTime.now().getYear(), LocalDateTime.now().getMonth(), LocalDateTime.now().plusDays(3).getDayOfMonth(), 6, 45);

        // PCM 1관 | 인셉션 | 모레, 글피
        for(int openTimeChangeNum=1; openTimeChangeNum<=6; openTimeChangeNum++) {

            standardTimePlusDay2 = standardTimePlusDay2.plusMinutes(25);
            standardTimePlusDay3 = standardTimePlusDay3.plusMinutes(25);

            OpenCinemaRoom openCinemaRoomP2 = new OpenCinemaRoom(standardTimePlusDay2, cinema1Room2, INCEPTION);
            OpenCinemaRoom openCinemaRoomP3 = new OpenCinemaRoom(standardTimePlusDay3, cinema1Room2, INCEPTION);
            openCinemaRoomRepository.save(openCinemaRoomP2);
            openCinemaRoomRepository.save(openCinemaRoomP3);

            standardTimePlusDay2 = standardTimePlusDay2.plusHours(2).plusMinutes(28);
            standardTimePlusDay3 = standardTimePlusDay3.plusHours(2).plusMinutes(28);
        }

        standardTimePlusDay2 = LocalDateTime.of
                (LocalDateTime.now().getYear(), LocalDateTime.now().getMonth(), LocalDateTime.now().plusDays(2).getDayOfMonth(), 6, 45);
        standardTimePlusDay3 = LocalDateTime.of
                (LocalDateTime.now().getYear(), LocalDateTime.now().getMonth(), LocalDateTime.now().plusDays(3).getDayOfMonth(), 6, 45);

        for(int openTimeChangeNum=1; openTimeChangeNum<=7; openTimeChangeNum++) {

            standardTimePlusDay2 = standardTimePlusDay2.plusMinutes(25);
            standardTimePlusDay3 = standardTimePlusDay3.plusMinutes(25);

            OpenCinemaRoom openCinemaRoomP2 = new OpenCinemaRoom(standardTimePlusDay2, cinema1Room3, TopGun_Maverick);
            OpenCinemaRoom openCinemaRoomP3 = new OpenCinemaRoom(standardTimePlusDay3, cinema1Room3, TopGun_Maverick);
            openCinemaRoomRepository.save(openCinemaRoomP2);
            openCinemaRoomRepository.save(openCinemaRoomP3);

            standardTimePlusDay2 = standardTimePlusDay2.plusHours(2).plusMinutes(10);
            standardTimePlusDay3 = standardTimePlusDay3.plusHours(2).plusMinutes(10);

        }

        standardTimePlusDay2 = LocalDateTime.of
                (LocalDateTime.now().getYear(), LocalDateTime.now().getMonth(), LocalDateTime.now().plusDays(2).getDayOfMonth(), 6, 45);
        standardTimePlusDay3 = LocalDateTime.of
                (LocalDateTime.now().getYear(), LocalDateTime.now().getMonth(), LocalDateTime.now().plusDays(3).getDayOfMonth(), 6, 45);

        for(int openTimeChangeNum=1; openTimeChangeNum<=3; openTimeChangeNum++) {

            standardTimePlusDay2 = standardTimePlusDay2.plusMinutes(25);
            standardTimePlusDay3 = standardTimePlusDay3.plusMinutes(25);

            OpenCinemaRoom openCinemaRoomP2 = new OpenCinemaRoom(standardTimePlusDay2, cinema1Room3, TITANIC);
            OpenCinemaRoom openCinemaRoomP3 = new OpenCinemaRoom(standardTimePlusDay3, cinema1Room3, TITANIC);
            openCinemaRoomRepository.save(openCinemaRoomP2);
            openCinemaRoomRepository.save(openCinemaRoomP3);

            standardTimePlusDay2 = standardTimePlusDay2.plusHours(3).plusMinutes(15);
            standardTimePlusDay3 = standardTimePlusDay3.plusHours(3).plusMinutes(15);

        }

        LocalDateTime standardTime2PlusDay2 = LocalDateTime.of
                (LocalDateTime.now().getYear(), LocalDateTime.now().getMonth(), LocalDateTime.now().plusDays(2).getDayOfMonth(), 18, 10);

        LocalDateTime standardTime2PlusDay3 = LocalDateTime.of
                (LocalDateTime.now().getYear(), LocalDateTime.now().getMonth(), LocalDateTime.now().plusDays(3).getDayOfMonth(), 18, 10);

        for(int openTimeChangeNum=1; openTimeChangeNum<=3; openTimeChangeNum++) {

            standardTime2PlusDay2 = standardTime2PlusDay2.plusMinutes(25);
            standardTime2PlusDay3 = standardTime2PlusDay3.plusMinutes(25);

            OpenCinemaRoom openCinemaRoomP2 = new OpenCinemaRoom(standardTime2PlusDay2, cinema1Room3, Whiplash);
            OpenCinemaRoom openCinemaRoomP3 = new OpenCinemaRoom(standardTime2PlusDay3, cinema1Room3, Whiplash);
            openCinemaRoomRepository.save(openCinemaRoomP2);
            openCinemaRoomRepository.save(openCinemaRoomP3);

            standardTime2PlusDay2 = standardTime2PlusDay2.plusHours(3).plusMinutes(15);
            standardTime2PlusDay3 = standardTime2PlusDay3.plusHours(3).plusMinutes(15);

        }

        Member chanmi = new Member("chanmi", "박찬미1234", "1234123412341234", "010-0000-0000");
        Member cute = new Member("cute1234", "귀요미1234", "1234123412341234", "010-1111-0000");
        Member memory = new Member("memory", "메모리1234", "1234123412341234", "010-2222-0000");
        Member client = new Member("client", "클라이언트1234", "1234123412341234", "010-3333-0000");
        Member member = new Member("member", "회원1234", "1234123412341234", "010-4444-0000");

        memberRepository.save(chanmi);
        memberRepository.save(cute);
        memberRepository.save(memory);
        memberRepository.save(client);
        memberRepository.save(member);


    }

}
