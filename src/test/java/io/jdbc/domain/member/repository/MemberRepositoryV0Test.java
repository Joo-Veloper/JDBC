package io.jdbc.domain.member.repository;

import io.jdbc.domain.member.entity.Member;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

class MemberRepositoryV0Test {

    MemberRepositoryV0 repository = new MemberRepositoryV0();
    @Test
    void crud() throws SQLException {
        //save
        Member member = new Member("memberV1", 10000);
        repository.save(member);
    }
}