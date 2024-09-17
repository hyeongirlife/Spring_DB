package com.example.demo.service;

import com.example.demo.domain.Member;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.MemberRepositoryV3;
import com.example.demo.repository.MemberRepositoryV4_1;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 트랜잭션 - AOP 적용
 */
@Slf4j
public class MemberServiceV4 {

    private final MemberRepository memberRepository;
// command + N
    public MemberServiceV4(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // !! 해당 서비스 로직을 실행하면 트랜잭션을 실행하고, 종료하겠다.
    @Transactional
    public void accountTransfer(String fromId, String toId, int money) {
            bizLogic(fromId, toId, money);
    }
// !! ctrl + alt + m => 코드를 메소드로 변경
    public void bizLogic( String fromId, String toId, int money) {

        Member fromMember = memberRepository.findById(fromId);
        Member toMember = memberRepository.findById(toId);
        memberRepository.update(fromId, fromMember.getMoney() - money);
        validation(toMember);
        memberRepository.update(toId, toMember.getMoney() + money);
    }

    private void validation(Member toMember) {
        if (toMember.getMemberId().equals("ex")) {
            throw new IllegalStateException("이체중 예외 발생"); }
    }

    public void release(Connection con) {
        if (con != null) {
            try {
                con.setAutoCommit(true); // 커넥션 풀 고려 con.close();
            } catch (Exception e) {
                log.info("error", e);
            }
        }
    }
    }

