package pcm.open_movie.controller.form.member;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class QuitMemberForm {

    private String memberId;

    private String password;
    private String checkPassword;

    public QuitMemberForm() {
    }

    public QuitMemberForm(String memberId, String password, String checkPassword) {
        this.memberId = memberId;
        this.password = password;
        this.checkPassword = checkPassword;
    }
}
