package kr.ac.jejunu.myproject.Guestbook;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestbookDao extends JpaRepository<Guestbook, Long>{

}
