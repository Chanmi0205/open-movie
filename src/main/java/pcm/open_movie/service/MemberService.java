package pcm.open_movie.service;

import pcm.open_movie.domain.dto.MemberDTO;
import pcm.open_movie.domain.entity.Member;

import java.util.Optional;

public interface MemberService {

    Member findMemberById(String memberId);

    String findMemberName(String memberId);

    boolean memberExist(String memberId);

    boolean memberExist(String memberId, String password);

    Member memberJoin(String memberId, String memberName, String password, String phoneNum);

    void memberSetting(String memberId, String memberName, String phoneNum);

    void memberSetting(String memberId, String memberName, String phoneNum, String password);

    void memberQuit(String memberId);

}
