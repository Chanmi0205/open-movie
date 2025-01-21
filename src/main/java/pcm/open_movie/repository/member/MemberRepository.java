package pcm.open_movie.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pcm.open_movie.domain.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {

    // 로그인 시 사이트를 이용할 때 기본적으로 제공되는 정보
    @Query("SELECT m.memberName FROM Member m WHERE m.memberId = :memberId")
    String findMemberNameByMemberId(@Param("memberId") String memberId);

    // 회원가입 시 아이디 중복 여부
    @Query("SELECT EXISTS (SELECT m FROM Member m WHERE m.memberId = :memberId)")
    boolean memberExists(@Param("memberId") String memberId);

    // 회원이 탈퇴 시, 로그인 시
    @Query("SELECT EXISTS (SELECT m FROM Member m WHERE m.memberId = :memberId AND m.password = :password)")
    boolean memberExists(@Param("memberId") String memberId, @Param("password") String password);

}
