package kr.ac.jejunu.myproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.ac.jejunu.myproject.domain.Guestbook;

public interface GuestbookDao extends JpaRepository<Guestbook, Long> {

}
