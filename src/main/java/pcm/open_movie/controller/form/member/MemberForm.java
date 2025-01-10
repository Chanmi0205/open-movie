package pcm.open_movie.controller.form.member;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

@Data
@Getter
@Setter
public class MemberForm {

    @NotBlank
    @Size(min = 5, max = 20)
    private String memberId;

    @NotBlank
    @Size(min = 5, max = 30)
    private String memberName;

    @NotBlank
    @Size(min = 15, max = 30)
    private String password;

    @NotBlank
    private String checkPassword;

    @NotBlank
    @Pattern(regexp = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$")
    private String phoneNum;

    public MemberForm() {
    }

    public MemberForm(String memberId, String memberName, String password, String checkPassword, String phoneNum) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.password = password;
        this.checkPassword = checkPassword;
        this.phoneNum = phoneNum;
    }
}
