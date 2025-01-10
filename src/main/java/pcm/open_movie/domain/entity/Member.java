package pcm.open_movie.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
public class Member extends UserCRUD {

    @Id
    @Column(name = "MEMBER_ID")
    private String memberId;

    private String memberName;

    private String password;

    private String phoneNum;

    @OneToMany(mappedBy = "member")
    private List<MovieReserve> movieReserveList = new ArrayList<>();

    public Member() {
    }

    public Member(String memberId, String memberName, String password, String phoneNum) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.password = password;
        this.phoneNum = phoneNum;
    }

    public void changeMember(String memberId, String memberName, String phoneNum, String password) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.phoneNum = phoneNum;
        this.password = password;
    }

    public void changeMember(String memberId, String memberName, String phoneNum) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.phoneNum = phoneNum;
    }

}
