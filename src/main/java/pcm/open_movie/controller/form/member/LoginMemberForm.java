package pcm.open_movie.controller.form.member;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class LoginMemberForm {

    private String memberId;
    private String password;

    public LoginMemberForm() {
    }

    public LoginMemberForm(String memberId, String password) {
        this.memberId = memberId;
        this.password = password;
    }
}
