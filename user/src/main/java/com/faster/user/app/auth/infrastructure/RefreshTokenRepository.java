package com.faster.user.app.auth.infrastructure;

import com.faster.user.app.auth.domain.RefreshTokenEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {
  Optional<RefreshTokenEntity> findByUserId(Long userId);

}
