package com.example.demo.domain;

import lombok.Data;

@Data
public class Member {

    private String memberId;
    private int money;

    public Member() {

    }

    public Member(String memberId, int money) {
        this.memberId = memberId;
        this.money = money;
    }

    public Member(String memberId) {
    }
}
