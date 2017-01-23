package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.UserSku;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the UserSku entity.
 */
@SuppressWarnings("unused")
public interface UserSkuRepository extends JpaRepository<UserSku,Long> {

    @Query("select userSku from UserSku userSku where userSku.user.login = ?#{principal.username}")
    List<UserSku> findByUserIsCurrentUser();

}
