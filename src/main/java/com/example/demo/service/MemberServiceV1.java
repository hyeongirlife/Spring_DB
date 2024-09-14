package com.example.demo.service;

import lombok.RequiredArgsConstructor;
import com.example.demo.domain.Member;
import com.example.demo.repository.MemberRepositoryV1;

import java.sql.SQLException;

@RequiredArgsConstructor
public class MemberServiceV1 {

    private final MemberRepositoryV1 memberRepository;
    // !! 트랜잭션은 서비스 계층에서 수행되어야 한다.
    public void accountTransfer(String fromId, String toId, int money) throws SQLException {
        Member fromMember = memberRepository.findById(fromId);
        Member toMember = memberRepository.findById(toId);

        // !! 출금
        memberRepository.update(fromId, fromMember.getMoney() - money);
        validation(toMember);
        // !! 입금
        memberRepository.update(toId, toMember.getMoney() + money);
    }

    private void validation (Member toMember) {
        if (toMember.getMemberId().equals("ex")) {
            throw new IllegalStateException("이체 중 예외 발생");
        }
    }

}
