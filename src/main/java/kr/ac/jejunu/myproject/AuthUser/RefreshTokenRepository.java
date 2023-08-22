package kr.ac.jejunu.myproject.AuthUser;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByRefreshToken(String refreshToken);
    //생성
    boolean existsByKeyEmailAndUserAgent(String userEmail, String userAgent);

    //생성
    void deleteByKeyEmailAndUserAgent(String userEmail, String userAgent);
}
