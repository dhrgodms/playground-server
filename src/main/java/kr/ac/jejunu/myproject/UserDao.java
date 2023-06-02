package kr.ac.jejunu.myproject;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User,Long> {
//    return userDao.findById(id);
}
