package pcm.open_movie.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class MovieReserve extends UserCRUD {

    @GeneratedValue
    @Id
    private Long movieReserveId;

    // 외래키 방향 확인
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OPEN_CINEMA_ROOM_ID")
    private OpenCinemaRoom openCinemaRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CINEMA_ROOM_SITE_ID")
    private CinemaRoomSite cinemaRoomSite;

    public MovieReserve() {
    }

    public MovieReserve(CinemaRoomSite cinemaRoomSite, OpenCinemaRoom openCinemaRoom, Member member) {
        this.cinemaRoomSite = cinemaRoomSite;
        this.openCinemaRoom = openCinemaRoom;
        this.member = member;
        member.getMovieReserveList().add(this);
    }

    // 회원이 예매한 영화를 변경할 때(상영 영화관, 좌석 변경)
    public void changeMemberAndOpenMovie(Member member, OpenCinemaRoom openCinemaRoom, CinemaRoomSite cinemaRoomSite) {
        this.member = member;
        member.getMovieReserveList().add(this);
        // 상영중인 영화관의 관PK와 예매하고자 하는 관PK가 같은지 확인
        if(openCinemaRoom.getCinemaRoom().getCinemaRoomId().equals(cinemaRoomSite.getCinemaRoom().getCinemaRoomId())) {
            this.openCinemaRoom = openCinemaRoom;
            this.cinemaRoomSite = cinemaRoomSite;
        }
    }

}
