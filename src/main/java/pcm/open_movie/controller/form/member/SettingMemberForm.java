package pcm.open_movie.controller.form.member;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

@Data
@Getter
@Setter
public class SettingMemberForm {

    @Size(min = 5, max = 20)
    private String memberId;

    @Size(min = 5, max = 30)
    private String memberName;
    private String password;
    private String checkPassword;

    @Pattern(regexp = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$")
    private String phoneNum;

    public SettingMemberForm() {
    }

    public void defaultSetting(String memberId, String memberName, String phoneNum) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.phoneNum = phoneNum;
    }

    public SettingMemberForm(String memberId, String memberName, String password, String checkPassword, String phoneNum) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.password = password;
        this.checkPassword = checkPassword;
        this.phoneNum = phoneNum;
    }
}
