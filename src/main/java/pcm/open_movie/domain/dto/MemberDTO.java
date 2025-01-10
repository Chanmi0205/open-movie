package pcm.open_movie.domain.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class MemberDTO {

    private String memberId;
    private String memberName;
    private String phoneNum;

    public MemberDTO() {
    }

    public MemberDTO(String memberId, String memberName, String phoneNum) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.phoneNum = phoneNum;
    }
}
