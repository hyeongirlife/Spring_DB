package com.example.demo.service;

import com.example.demo.domain.Member;
import com.example.demo.repository.MemberRepositoryV2;
import com.example.demo.repository.MemberRepositoryV3;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 트랜잭션 - 트랜잭션 매니저
 */
@Slf4j
@RequiredArgsConstructor
public class MemberServiceV3_1 {

    // !! transactionManager에 JDBC, JPA, HIBERNATE 등 여러 ORM을 변경할 수 있다.
    private final PlatformTransactionManager transactionManager;
    private final MemberRepositoryV3 memberRepository;
    // !! 트랜잭션은 서비스 계층에서 수행되어야 한다.
    public void accountTransfer(String fromId, String toId, int money) throws SQLException {
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try{
            bizLogic(fromId, toId, money);
            transactionManager.commit(status);
        }catch(Exception e) {
            transactionManager.rollback(status);
            throw new IllegalStateException(e);
        }
    }
// !! ctrl + alt + m => 코드를 메소드로 변경
    public void bizLogic( String fromId, String toId, int money) throws SQLException {

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

