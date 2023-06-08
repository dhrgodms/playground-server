package kr.ac.jejunu.myproject;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberDao extends JpaRepository<Member,Long> {
    Optional<Member> findByName(String name);
}