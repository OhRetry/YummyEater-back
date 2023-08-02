package com.YammyEater.demo.repository.user;

import com.YammyEater.demo.domain.user.RefreshTokenInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenInfoRepository extends JpaRepository<RefreshTokenInfo, String> {

}
