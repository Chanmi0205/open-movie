package pcm.open_movie.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pcm.open_movie.domain.entity.Member;
import pcm.open_movie.repository.member.MemberRepository;
import pcm.open_movie.service.MemberService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public Member findMemberById(String memberId) {
        return memberRepository.findById(memberId).orElse(null);
    }

    @Override
    public String findMemberName(String memberId) {
        return memberRepository.findMemberNameByMemberId(memberId);
    }

    @Override
    public boolean memberExist(String memberId) {
        return memberRepository.memberExists(memberId);
    }

    @Override
    public boolean memberExist(String memberId, String password) {
        return !memberRepository.memberExists(memberId, password);
    }

    @Override
    public Member memberJoin(String memberId, String memberName, String password, String phoneNum) {
        Member member = new Member(memberId, memberName, password, phoneNum);
        memberRepository.save(member);
        return member;
    }

    @Override
    public void memberSetting(String memberId, String memberName, String phoneNum) {
        Member member = memberRepository.findById(memberId).orElse(null);
        assert member != null;
        member.changeMember(memberId, memberName, phoneNum);
    }

    @Override
    public void memberSetting(String memberId, String memberName, String phoneNum, String password) {
        Member member = memberRepository.findById(memberId).orElse(null);
        assert member != null;
        member.changeMember(memberId, memberName, phoneNum, password);
    }

    @Override
    public void memberQuit(String memberId) {
        memberRepository.deleteById(memberId);
    }
}
