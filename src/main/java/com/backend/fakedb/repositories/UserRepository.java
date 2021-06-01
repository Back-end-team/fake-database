package com.backend.fakedb.repositories;

import com.backend.fakedb.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    @Modifying
    @Query("UPDATE UserEntity u SET u.passwordHash = ?2, u.avatarUrl = ?3, u.bio = ?4 WHERE u.id = ?1")
    void update(Integer id, String password, String avatar, String bio);
}
