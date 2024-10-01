package io.jdbc.domain.member.repository;

import io.jdbc.domain.member.entity.Member;

public interface MemberRepository {
    Member save(Member member);
    Member findById(String memberId);
    void update(String memberId, int money);
    void delete(String memberId);
}