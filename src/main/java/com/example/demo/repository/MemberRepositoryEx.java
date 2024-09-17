package com.example.demo.repository;

import com.example.demo.domain.Member;

import java.sql.SQLException;

/**
 * 특정 기술에 종속된 인터페이스 ex) SQLException
 */
public interface MemberRepositoryEx {
    Member save(Member member) throws SQLException;
    Member findById(String memberId) throws SQLException;
    void update(String memberId,int money) throws SQLException;
    void delete(String memberId) throws SQLException;
}
